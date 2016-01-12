
package de.weltraumschaf.maven.infallible;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 */
public class ResultFormatterTest {

    private final String NL = ResultFormatter.NL;

    private final ResultFormatter sut = new ResultFormatter();

    @Test
    public void testFormatResult_empty() {
        final Collector tested = new Collector();

        assertThat(
            sut.format(tested),
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
            sut.format(tested),
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
            sut.format(tested),
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