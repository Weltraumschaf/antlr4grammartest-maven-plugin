
package de.weltraumschaf.maven.infallible;

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link ParserFactory}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class ParserFactoryTest {

    @Test
    public void testGenerateClassName_withEmptyPackage() {
        assertThat(ParserFactory.generateClassName("", "Foo", "Bar"), is("FooBar"));
    }

    @Test
    public void testGenerateClassName_withPackage() {
        assertThat(ParserFactory.generateClassName("de.weltraumschaf", "Foo", "Bar"),
            is("de.weltraumschaf.FooBar"));
    }


}