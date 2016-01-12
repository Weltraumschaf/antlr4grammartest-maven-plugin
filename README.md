# Infallible Maven Plugin

The use  case of  this plugin  is that  you have  made your  own [ANTLR4][antlr]
grammar and you have  lots of files with example "source  code" of your language
defined by  that grammar.  Now you  want to  throw all  these files  against the
generated laexer/parser to verify they will be parsed without any errors.

This  is   inspired  by   the  [grammartestplugin][grammartestmojo]   from  [Tom
Everett](http://blog.khubla.com/).

## Usage

For example you have defined a grammar named _Snafu_ and the source files have
the file extension _*.snf_. Your [Maven][mvn] project has a layout like:

    [...]
      +- pom.xml
      +- src/
          +- main/
          |   +- antlr4/
          |   |    +- Snafu.g4
          |   +- java/
          |        +- [...]
          +- test/
              +- snafu/
              |    +- simple/
              |    |    +- constant.snf
              |    |    +- variable.snf
              |    |    +- if.snf
              |    |    +- [...]
              |    +- complex/
              |         +- hello_world.snf
              |         +- faculty.snf
              |    |    +- [...]
              +- java/
                   +- [...]

The grammar file (_Snafu.g4_) looks like (unimportant details ommited):

    // (1) Name of grammar.
    grammar Snafu;

    // (2) Package for the ANTLR genrated classes.
    @header {
    package foo.bar.baz;
    }

    // (3) This is the main entry production rule.
    startRule   : (statement EOL)* ;
    ...

Then the configuration in the POM should look like:

    ...
    <build>
        <plugins>
            <plugin>
                <groupId>de.weltraumschaf.maven</groupId>
                <artifactId>infallible-maven-plugin</artifactId>
                <version>1.0.0-alpha</version>
                
                <configuration>
                    <!-- (1) This is the name of the grammar. -->
                    <grammarName>Snafu</grammarName>
                    <!-- (2) Package name of parser/lexer. -->
                    <packageName>foo.bar.baz</packageName>
                    <!-- (3) This is the name of the main entry production rule. -->
                    <startRule>startRule</startRule>
                    
                    <filesets>
                        <fileset>
                            <!-- Base direcotry where to find the sources. -->
                            <directory>src/test/snafu</directory>

                            <includes>
                                <!-- Include every file with .snf extension in all subdirecotries. -->
                                <include>**/*.snf</include>
                            </includes>
                            
                            <excludes>
                                <!-- Exclude log files. -->
                                <exclude>**/*.log</exclude>
                            </excludes>
                        </fileset>
                    </filesets>
                </configuration>
                
                <executions>
                    <execution>
                        <id>parse-all-files</id>

                        <goals>
                            <goal>parse</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    ...

The `<fileset>`  configuration works as  in the  most other Maven  plugins. This
plugin provides one  goal (`test`) which runs  by default in the  test phase. So
just type `mvn test` to run it.

Of course  the [ANTLR4 Maven  plugin][antlr-plugin] must run before  to generate
the lexer/parser classes. How to do that is documented [here][antlr-plugin-doc].

## History – Where It Comes From

While  playing  around with  [ANTLR4][antlr]  I  stidied the  [grammars  examble
repo][grammars] on  GitHub. While  doing that I  stumbled upon  the [grammartest
plugin][grammartestmojo]. A  very helpful tool  which parses your code  with the
genrated lexer/parse to show errors in the examples or the grammar.

But I had some problems with this tool:

- It  is not released  into Maven Central.  So if you want  to use it,  you must
build it on your machine.
- It does not support filesets like most other Maven plugins.
- It fails on the first error instead of collecting them all.
- The errors are wrapped by maven exceptions and so not always helpful.

So I decided to write the whole plugin from scratch with these requirements:

- The plugina may be skipped by `-Dinfallible.skip=true` which is a defacto Maven standart.
- It supports file sets.
- Does not fail on first error, but collects them and then fails at the end of execution.
- Use Maven internal logging.
- Better testcoverage.

## The Name

The  name   _Infallible  Maven  plugin_  is   a  funny  joke  inspired   by  the
[Failsafe][failsafe] and  [Surefire][surefire] Maven plugins: Both  are synonyms
for "dead certain". Also infallible is a synonym for that.

[antlr]:            http://www.antlr.org
[antlr-plugin]:     http://www.antlr.org/api/maven-plugin/latest/
[antlr-plugin-doc]: http://www.antlr.org/api/maven-plugin/latest/usage.html
[mvn]:              https://maven.apache.org/
[failsafe]:         http://maven.apache.org/surefire/maven-failsafe-plugin/index.html
[surefire]:         https://maven.apache.org/surefire/maven-surefire-plugin/
[grammars]:         https://github.com/antlr/grammars-v4/
[grammartestmojo]:  https://github.com/antlr/grammars-v4/tree/master/support/antlr4test-maven-plugin