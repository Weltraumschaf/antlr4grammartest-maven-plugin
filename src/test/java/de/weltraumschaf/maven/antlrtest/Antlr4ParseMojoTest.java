
package de.weltraumschaf.maven.antlrtest;

import java.io.File;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import static org.codehaus.plexus.PlexusTestCase.getTestFile;
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

    private static final String FIXTURE_POM = "src/test/resources/fixture-pom.xml";
    private Antlr4ParseMojo sut;

    @Override
   protected void setUp() throws Exception {
      super.setUp();
    final File pom = getTestFile(FIXTURE_POM);
         assertThat(pom, is(not(nullValue())));
         assertThat(pom.exists(), is(true));
         sut = (Antlr4ParseMojo) lookupMojo(Antlr4ParseMojo.GOAL, pom);
   }

    @Test
    public void testSomeMethod() {
    }

}