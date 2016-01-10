package de.weltraumschaf.maven.antlrtest;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collects results.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class Collector {

    private final Collection<Result> results = new ArrayList<>();

    void add(final Result result) {
        results.add(result);
    }

    long count() {
        return results.size();
    }

    long countFailed() {
        return results.stream().filter(r -> r.isFailed()).count();
    }

    long countPassed() {
        return count() - countFailed();
    }
}
