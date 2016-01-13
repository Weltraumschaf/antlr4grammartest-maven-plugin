package de.weltraumschaf.maven.infallible;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import org.apache.commons.lang3.Validate;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * This factory creates a class loader which looks inside a given directory for classes.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class ClassLoaderFactory {
    /**
     * Where to look for classes.
     */
    private final File baseDir;

    /**
     * Dedicated constructor.
     *
     * @param baseDir  must not be {@code null}
     */
    ClassLoaderFactory(final File baseDir) {
        super();
        this.baseDir = Validate.notNull(baseDir, "Parameter 'baseDir' must not be null!");
    }

    /**
     * Creates the class loader.
     *
     * @return never {@code null}, always new instance
     * @throws MojoExecutionException if the {@link #baseDir} results in a mal formed URL
     */
    ClassLoader getClassLoader() throws MojoExecutionException {
        try {
            return new URLClassLoader(
                new URL[]{baseDir.toURI().toURL()},
                Thread.currentThread().getContextClassLoader());
        } catch (final MalformedURLException ex) {
            throw new MojoExecutionException(
                String.format("Can't obtain class loader for '%s' (%s)!", baseDir, ex.getMessage()), ex);
        }
    }
}
