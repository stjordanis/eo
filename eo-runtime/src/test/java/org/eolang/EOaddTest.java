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
package org.eolang;

import org.eolang.sys.ArgsException;
import org.eolang.sys.ArgsOf;
import org.eolang.sys.Entry;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link AddOf}.
 *
 * @since 0.1
 */
public final class EOaddTest {

    @Test
    public void addsTwo() throws Exception {
        MatcherAssert.assertThat(
            new EOadd(
                new ArgsOf(new Entry("01", 1L), new Entry("02", -1L))
            ).call(),
            Matchers.equalTo(0L)
        );
    }

    @Test
    public void addsOneArg() throws Exception {
        MatcherAssert.assertThat(
            new EOadd(
                new ArgsOf(new Entry("01", 1L))
            ).call(),
            Matchers.equalTo(1L)
        );
    }

    @Test
    public void addsNoArgs() {
        Assertions.assertThrows(
            ArgsException.class,
            () -> new EOadd(new ArgsOf()).call()
        );
    }

}
