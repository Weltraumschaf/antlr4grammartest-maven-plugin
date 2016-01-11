package de.weltraumschaf.maven.infallible;

import java.io.File;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.shared.model.fileset.FileSet;
import static org.codehaus.plexus.PlexusTestCase.getTestFile;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link Antlr4ParseMojo}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Antlr4ParseMojoTest extends AbstractMojoTestCase {

    private final String NL = String.format("%n");
    private static final String FIXTURE_POM = "src/test/resources/fixture-pom.xml";
    private Antlr4ParseMojo sut;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        final File pom = getTestFile(FIXTURE_POM);
        assertThat(pom, is(not(nullValue())));
        assertThat(pom.exists(), is(true));
        sut = (Antlr4ParseMojo) lookupMojo(Antlr4ParseMojo.GOAL, pom);
        assertThat(sut, is(not(nullValue())));
    }

    @Test
    public void testConfigurationFromPom() {
        assertThat(sut.isSkip(), is(false));
        assertThat(sut.getStartRule(), is("startRule"));
        assertThat(sut.getGrammarName(), is("Snafu"));
        assertThat(sut.getPackageName(), is("foo.bar.baz"));
        assertThat(sut.getEncoding(), is(Antlr4ParseMojo.DEFAULT_ENCODING));
        assertThat(sut.getFilesets(), is(not(nullValue())));
        assertThat(sut.getFilesets().length, is(1));

        final FileSet fileset = sut.getFilesets()[0];
        assertThat(fileset.getDirectory(), is("src/test/snafu"));
        assertThat(fileset.getIncludes(), hasSize(1));
        assertThat(fileset.getIncludes(), contains("**/*.snf"));
        assertThat(fileset.getExcludes(), hasSize(1));
        assertThat(fileset.getExcludes(), contains("**/*.log"));
    }

    @Test
    public void testGenerateClassName_withEmptyPackage() {
        assertThat(Antlr4ParseMojo.generateClassName("", "Foo", "Bar"), is("FooBar"));
    }

    @Test
    public void testGenerateClassName_withPackage() {
        assertThat(
            Antlr4ParseMojo.generateClassName("de.weltraumschaf", "Foo", "Bar"),
            is("de.weltraumschaf.FooBar"));
    }

    @Test
    public void testGetFilesToTest() {
        assertThat(sut.getFilesToTest(), hasSize(3));
        assertThat(
            sut.getFilesToTest(),
            containsInAnyOrder(
                "src/test/snafu/some.snf",
                "src/test/snafu/with_errors.snf",
                "src/test/snafu/without_errors.snf"));
    }

    @Test
    public void testPrintStartInfo() throws MojoExecutionException, MojoFailureException {
        assertThat(
            sut.formatStartInfo(),
            is("-------------------------------------------------------" + NL
                + "ANTLR4 Grammar Test" + NL
                + "-------------------------------------------------------"));
    }

    @Test
    public void testFormatResult_empty() {
        final Collector tested = new Collector();

        assertThat(
            sut.formatResult(tested),
            is(
                "Results:" + NL
                + NL
                + "Sources parsed: 0, Failed: 0" + NL));
    }

    @Test
    public void testFormatResult_allPassed() {
        final Collector tested = new Collector();
        tested.add(Result.passed("foo.snf"));
        tested.add(Result.passed("bar.snf"));
        tested.add(Result.passed("baz.snf"));

        assertThat(
            sut.formatResult(tested),
            is(
                "Results:" + NL
                + NL
                + "Sources parsed: 3, Failed: 0" + NL));
    }

    @Test
    public void testFormatResult_someFailed() {
        final Collector tested = new Collector();
        tested.add(Result.passed("foo.snf"));
        tested.add(Result.failed("bar.snf", new ParseCancellationException("Snafu one!")));
        tested.add(Result.failed("baz.snf", new ParseCancellationException("Snafu two!")));

        assertThat(
            sut.formatResult(tested),
            is(
                "Results:" + NL
                + NL
                + "Failed sources:" + NL
                + "  bar.snf" + NL
                + "    Snafu one!" + NL
                + "  baz.snf" + NL
                + "    Snafu two!" + NL
                + NL
                + "Sources parsed: 3, Failed: 2" + NL));
    }

}
