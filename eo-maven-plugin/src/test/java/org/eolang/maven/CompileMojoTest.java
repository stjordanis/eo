/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.eolang.maven;

import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.cactoos.io.InputOf;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;
import org.cactoos.scalar.LengthOf;
import org.eolang.compiler.CompileException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test case for {@link CompileMojo}.
 *
 * @since 0.1
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class CompileMojoTest extends AbstractMojoTestCase {

    /**
     * Temp dir for tests.
     * @checkstyle VisibilityModifierCheck (4 lines)
     */
    @TempDir
    public Path temp;

    @Test
    public void testSimpleCompilation() throws Exception {
        final CompileMojo mojo = new CompileMojo();
        final Path src = this.temp.resolve("src");
        this.setVariableValueToObject(mojo, "sourcesDirectory", src.toFile());
        new LengthOf(
            new TeeInput(
                new InputOf(
                    "[args] > main\n  (stdout \"Hello!\").print\n"
                ),
                new OutputTo(src.resolve("main.eo"))
            )
        ).value();
        final Path target = this.temp.resolve("target");
        this.setVariableValueToObject(
            mojo, "targetDir", target.toFile()
        );
        final Path generated = this.temp.resolve("generated");
        this.setVariableValueToObject(
            mojo, "generatedDir", generated.toFile()
        );
        this.setVariableValueToObject(mojo, "project", new MavenProjectStub());
        mojo.execute();
        MatcherAssert.assertThat(
            Files.exists(generated.resolve("EOmain.java")),
            Matchers.is(true)
        );
    }

    @Test
    public void testCrashOnInvalidSyntax() throws Exception {
        final CompileMojo mojo = new CompileMojo();
        final Path src = this.temp.resolve("src");
        this.setVariableValueToObject(mojo, "sourcesDirectory", src.toFile());
        this.setVariableValueToObject(
            mojo, "generatedDir", this.temp.resolve("generated").toFile()
        );
        this.setVariableValueToObject(
            mojo, "targetDir", this.temp.resolve("target").toFile()
        );
        new LengthOf(
            new TeeInput(
                new InputOf("something is wrong here"),
                new OutputTo(src.resolve("f.eo"))
            )
        ).value();
        Assertions.assertThrows(
            CompileException.class,
            mojo::execute
        );
    }

}
