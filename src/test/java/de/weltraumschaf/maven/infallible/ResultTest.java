
package de.weltraumschaf.maven.infallible;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link Result}.
 */
public class ResultTest {

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(Result.class).verify();
    }

}