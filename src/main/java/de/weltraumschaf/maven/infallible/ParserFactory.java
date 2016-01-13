package de.weltraumschaf.maven.infallible;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;
import org.apache.commons.lang3.Validate;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/**
 * This class abstracts the whole creation of an ANTLR4 generated parser with all dependencies.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class ParserFactory {

    /**
     * Logging facility.
     */
    private final Log log;
    /**
     * Used to load generated ANTLR4 classes.
     */
    private final ClassLoader classes;
    /**
     * Optional package name of generated classes.
     */
    private final String packageName;
    /**
     * NAme of the ANTLR4 grammar.
     */
    private final String grammarName;

    /**
     * Dedicated constructor.
     *
     * @param log must not be {@code null}
     * @param classes must not be {@code null}
     * @param packageName must not be {@code null}
     * @param grammarName must not be {@code null}, empty or blank
     */
    ParserFactory(final Log log, final ClassLoader classes, final String packageName, final String grammarName) {
        super();
        this.log = Validate.notNull(log, "Parameter 'log' must not be null!");
        this.classes = Validate.notNull(classes, "Parameter 'classes' must not be null!");
        this.packageName = Validate.notNull(packageName, "Parameter 'packageName' must not be null!");
        this.grammarName = Validate.notBlank(grammarName, "Parameter 'grammarName' must not be null, empty or blank!");
    }

    Parser create(final Path sourcetoParse, final String encoding) throws MojoExecutionException {
        try {
            final Lexer lexer = (Lexer) createLexerConstructor().newInstance(new ANTLRFileStream(sourcetoParse.toString(), encoding));
            final Parser parser = (Parser) createParserConstructor().newInstance(new CommonTokenStream(lexer));
            parser.setErrorHandler(new BailErrorStrategy());
            return parser;
        } catch (final InstantiationException ex) {
            throw new MojoExecutionException(String.format("TODO (%s)", ex.getMessage()), ex);
        } catch (final IllegalAccessException ex) {
            throw new MojoExecutionException(String.format("TODO (%s)", ex.getMessage()), ex);
        } catch (final IllegalArgumentException ex) {
            throw new MojoExecutionException(String.format("TODO (%s)", ex.getMessage()), ex);
        } catch (final InvocationTargetException ex) {
            throw new MojoExecutionException(String.format("TODO (%s)", ex.getMessage()), ex);
        } catch (final IOException ex) {
            throw new MojoExecutionException(String.format("TODO (%s)", ex.getMessage()), ex);
        }
    }

    final Constructor<? extends Lexer> createLexerConstructor() throws MojoExecutionException {
        final String lexerClassName = generateClassName(packageName, grammarName, "Lexer");
        log.info(String.format("Using lexer class '%s'.", lexerClassName));
        final Class<? extends Lexer> lexerClass = createClass(lexerClassName, Lexer.class);

        try {
            return lexerClass.getConstructor(CharStream.class);
        } catch (final NoSuchMethodException | SecurityException ex) {
            throw new MojoExecutionException(String.format("Can not get constructor for '%s'!", lexerClassName));
        }
    }

    final Constructor<? extends Parser> createParserConstructor() throws MojoExecutionException {
        final String parserClassName = generateClassName(packageName, grammarName, "Parser");
        log.info(String.format("Using parser class '%s'.", parserClassName));
        final Class<? extends Parser> parserClass = createClass(parserClassName, Parser.class);

        try {
            return parserClass.getConstructor(TokenStream.class);
        } catch (final NoSuchMethodException | SecurityException ex) {
            throw new MojoExecutionException(String.format("Can not get constructor for '%s'!", parserClassName));
        }
    }

    private <U> Class<? extends U> createClass(final String name, final Class<U> superType) throws MojoExecutionException {
        try {
            return classes.loadClass(name).asSubclass(superType);
        } catch (final ClassNotFoundException ex) {
            throw new MojoExecutionException(
                String.format("Can not create class '%s' (%s)!", name, ex.getMessage()), ex);
        }
    }

    static String generateClassName(final String packageName, final String grammarName, final String suffix) {
        final StringBuilder buffer = new StringBuilder();

        if (!packageName.isEmpty()) {
            buffer.append(packageName).append('.');
        }

        buffer.append(grammarName).append(suffix);
        return buffer.toString();
    }
}
