package de.weltraumschaf.maven.antlrtest;

import org.apache.maven.model.FileSet;
import org.apache.maven.plugin.AbstractMojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * This mojo searches for files with language code to test against parser generated by given grammar.
 * <p>
 * Example configuration:
 * </p>
 * <pre>{@code
 *  <plugin>
 *      <groupId>de.weltraumschaf.maven</groupId>
 *      <artifactId>antlr4grammartest-maven-plugin</artifactId>
 *      <version>1.0.0</version>
 *
 *      <configuration>
 *          <skip>false</skip>
 *          <startRule>compilationUnit</startRule>
 *          <grammarName>Snafu</grammarName>
 *          <packageName>foo.bar.baz</packageName>
 *          <filesets>
 *              <fileset>
 *                  <directory>some/relative/path</directory>
 *                  <includes>
 *                      <include>**&#47;*.snf</include>
 *                  </includes>
 *                  <excludes>
 *                      <exclude>**&#47;log.log</exclude>
 *                  </excludes>
 *                  <followSymlinks>false</followSymlinks>
 *              </fileset>
 *          </filesets>
 *      </configuration>
 * </plugin>
 * }</pre>
 *
 * XXX Maybe LifecyclePhase.INTEGRATION_TEST or LifecyclePhase.VERIFY is better.
 *
 * XXX Make it thread safe?
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
@Mojo(name = Antlr4ParseMojo.GOAL, defaultPhase = LifecyclePhase.TEST, requiresProject = true, threadSafe = false)
public final class Antlr4ParseMojo extends AbstractMojo {

    static final String GOAL = "test";

    /**
     * https://maven.apache.org/shared/file-management/examples/mojo.html
     */
    @Parameter
    private FileSet[] filesets;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
