package de.weltraumschaf.maven.antlrtest;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang3.Validate;

/**
 * Collects results.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class Collector {

    /**
     * Holds the result.
     */
    private final Collection<Result> results = new ArrayList<>();

    /**
     * Add an result.
     * <p>
     * Duplicates are not cared.
     * </p>
     *
     * @param result must not be {@code null}
     */
    void add(final Result result) {
        results.add(Validate.notNull(result, "Parameter 'result' must not be null!"));
    }

    /**
     * Number of all collected results ({@link #countPassed()} + {@link #countFailed()}).
     *
     * @return not negative
     */
    long count() {
        return results.size();
    }

    /**
     * Number of failed results ({@link #count()} - {@link #countPassed()}).
     *
     * @return not negative
     */
    long countFailed() {
        return results.stream().filter(r -> r.isFailed()).count();
    }

    /**
     * Number of passed results ({@link #count()} - {@link #countFailed()}).
     *
     * @return not negative
     */
    long countPassed() {
        return count() - countFailed();
    }
}
