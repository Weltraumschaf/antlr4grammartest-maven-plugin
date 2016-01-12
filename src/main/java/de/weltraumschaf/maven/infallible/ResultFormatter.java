package de.weltraumschaf.maven.infallible;

/**
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class ResultFormatter {

    static final String NL = String.format("%n");

    String format(final Collector tested) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("Results:").append(NL).append(NL);

        if (tested.hasFailed()) {
            buffer.append("Failed sources:").append(NL);
            tested.results().stream().filter(r -> r.isFailed()).forEach(r -> {
                buffer.append("  ").append(r.getTestedFile()).append(NL);
                buffer.append("    ").append(r.getError().getMessage()).append(NL);
            });
            buffer.append(NL);
        }

        buffer.append(String.format("Sources parsed: %d, Failed: %d%n", tested.count(), tested.countFailed()));
        return buffer.toString();
    }
}
