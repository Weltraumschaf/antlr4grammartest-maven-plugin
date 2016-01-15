
package de.weltraumschaf.maven.infallible;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATN;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ParserInvoker}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class ParserInvokerTest {

    @Test
    public void invoke() throws MojoExecutionException {
        final ParserStub parser = spy(new ParserStub(mock(TokenStream.class)));
        final ParserInvoker sut = new ParserInvoker(mock(Log.class), parser, "foobar");

        final Result result = sut.invoke();

        verify(parser, times(1)).foobar();
        assertThat(result.isFailed(), is(false));
        assertThat(result.getError(), is(nullValue()));
        assertThat(result.getTestedFile(), is("foobar.snf"));
    }

    private static class ParserStub extends Parser {

        public ParserStub(final TokenStream input) {
            super(input);
        }

        public void foobar() {
            // Called by subject under test.
        }

        @Override
        public String getSourceName() {
            return "foobar.snf";
        }

        @Override
        public String[] getTokenNames() {
            return new String[0];
        }

        @Override
        public String[] getRuleNames() {
            return new String[0];
        }

        @Override
        public String getGrammarFileName() {
            return "";
        }

        @Override
        public ATN getATN() {
            return null;
        }

    }
}