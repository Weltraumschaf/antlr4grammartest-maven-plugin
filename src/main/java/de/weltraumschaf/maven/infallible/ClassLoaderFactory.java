package de.weltraumschaf.maven.infallible;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import org.apache.commons.lang3.Validate;

/**
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class ClassLoaderFactory {
    private final File baseDir;

    ClassLoaderFactory(final File baseDir) {
        super();
        this.baseDir = Validate.notNull(baseDir, "Parameter 'baseDir' must not be null!");
    }

    ClassLoader getClassLoader() throws MalformedURLException {
        return new URLClassLoader(
            new URL[]{baseDir.toURI().toURL()},
            Thread.currentThread().getContextClassLoader());
    }
}
