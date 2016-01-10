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

    int count() {
        return results.size();
    }

    int countFailed() {
        int count = 0;

        for (Result result : results) {
            if (result.isFailed()) {
                ++count;
            }
        }

        return count;
    }

    int countPassed() {
        return count() - countFailed();
    }
}
