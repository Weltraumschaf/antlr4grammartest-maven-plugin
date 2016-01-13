package de.weltraumschaf.maven.infallible;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.commons.lang3.Validate;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/**
 * THis class abstracts the start rule invocation on a parser instance.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class ParserInvoker {

    /**
     * Logging facility.
     */
    private final Log log;
    /**
     * PArser on which the method is invoked.
     */
    private final Parser parser;
    /**
     * Name of invoked method.
     */
    private final String methodName;

    /**
     * Dedicated constructor.
     *
     * @param log must not be {@code null}
     * @param parser not be {@code null}
     * @param methodName not be {@code null}
     */
    ParserInvoker(final Log log, final Parser parser, final String methodName) {
        super();
        this.log = Validate.notNull(log, "Parameter 'log' must not be null!");
        this.parser = Validate.notNull(parser, "Parameter 'parser' must not be null!");
        this.methodName = Validate.notNull(methodName, "Parameter 'methodName' must not be null!");
    }

    /**
     * Invokes the given method on the given parser.
     *
     * @return never {@code null}, always new instance
     * @throws MojoExecutionException on any reflection error during invocation
     */
    Result invoke() throws MojoExecutionException {
        final String fileToTest =parser.getSourceName();
        try {
            final Method start = parser.getClass().getMethod(methodName);
            start.invoke(parser);
            return Result.passed(fileToTest);
        } catch (final ParseCancellationException ex) {
            log.error(ex.getMessage(), ex);
            return Result.failed(fileToTest, ex);
        } catch (final IllegalAccessException ex) {
            throw new MojoExecutionException(String.format("TODO (%s)", ex.getMessage()), ex);
        } catch (final IllegalArgumentException ex) {
            throw new MojoExecutionException(String.format("TODO (%s)", ex.getMessage()), ex);
        } catch (final InvocationTargetException ex) {
            throw new MojoExecutionException(String.format("TODO (%s)", ex.getMessage()), ex);
        } catch (final NoSuchMethodException ex) {
            throw new MojoExecutionException(String.format("TODO (%s)", ex.getMessage()), ex);
        } catch (final SecurityException ex) {
            throw new MojoExecutionException(String.format("TODO (%s)", ex.getMessage()), ex);
        }
    }
}
