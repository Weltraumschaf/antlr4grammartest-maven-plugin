package de.weltraumschaf.maven.antlrtest;

import java.util.Objects;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.commons.lang3.Validate;

/**
 * Describes the result of one parsed file.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class Result {

    /**
     * Indicates if the parse failed or not.
     */
    private final boolean failed;
    /**
     * The relative path string as collected from the Maven file set configuration.
     */
    private final String testedFile;
    /**
     * Holds the error if {@link #failed} is {@code true}, else {@code null}.
     */
    private final ParseCancellationException error;

    /**
     * Dedicated constructor.
     * <p>
     * Use {@link #passed(java.lang.String)} or
     * {@link #failed(java.lang.String, org.antlr.v4.runtime.misc.ParseCancellationException)} factory methods instead.
     * </p>
     *
     * @param failed {@code true} if failed, else {@code false}
     * @param testedFile must not be {@code null} or empty
     * @param error may be {@code null}
     */
    private Result(boolean failed, String testedFile, ParseCancellationException error) {
        super();
        this.failed = failed;
        this.testedFile = Validate.notEmpty(testedFile, "Parameter 'testedFile' must not be null or empty!");
        this.error = error;
    }

    /**
     * Factory method to create a result for passed parse run.
     *
     * @param testedFile must not be {@code null} or empty
     * @return never {@code null}, always new instance
     */
    static Result passed(final String testedFile) {
        return new Result(false, testedFile, null);
    }

    /**
     * Factory method to create a result for failed parse run.
     *
     * @param testedFile must not be {@code null} or empty
     * @param error must not be {@code null}
     * @return never {@code null}, always new instance
     */
    static Result failed(final String testedFile, final ParseCancellationException error) {
        return new Result(true, testedFile, Validate.notNull(error, "Parameter 'error' must not be null!"));
    }

    boolean isFailed() {
        return failed;
    }

    String getTestedFile() {
        return testedFile;
    }

    ParseCancellationException getError() {
        return error;
    }

    @Override
    public int hashCode() {
        return Objects.hash(failed, testedFile, error);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Result)) {
            return false;
        }

        final Result other = (Result) obj;
        return Objects.equals(failed, other.failed)
            && Objects.equals(testedFile, other.testedFile)
            && Objects.equals(error, other.error);
    }

    @Override
    public String toString() {
        return "Result{" + "failed=" + failed + ", testedFile=" + testedFile + ", error=" + error + '}';
    }

}
