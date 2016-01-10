
package de.weltraumschaf.maven.antlrtest;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link Collector}.
 */
public class CollectorTest {

    private final Collector sut = new Collector();

    @Test
    public void testAdd() {
        assertThat(sut.count(), is(0));

        sut.add(Result.passed("foo"));

        assertThat(sut.count(), is(1));

        sut.add(Result.failed("bar", mock(ParseCancellationException.class)));

        assertThat(sut.count(), is(2));
    }

    @Test
    public void testCount() {
        sut.add(Result.passed("foo"));
        sut.add(Result.passed("bar"));
        sut.add(Result.passed("baz"));
        sut.add(Result.failed("snafu", mock(ParseCancellationException.class)));
        sut.add(Result.failed("fubar", mock(ParseCancellationException.class)));

        assertThat(sut.count(), is(5));
        assertThat(sut.countPassed(), is(3));
        assertThat(sut.countFailed(), is(2));
    }
}