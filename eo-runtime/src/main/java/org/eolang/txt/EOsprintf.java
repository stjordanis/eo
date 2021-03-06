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

package org.eolang.txt;

import java.util.Collection;
import java.util.LinkedList;
import org.cactoos.iterable.Sorted;
import org.eolang.sys.Args;
import org.eolang.sys.Phi;

/**
 * Sprintf.
 *
 * @since 0.2
 */
public final class EOsprintf implements Phi {

    /**
     * Args.
     */
    private final Args args;

    /**
     * Ctor.
     * @param arg Args
     */
    public EOsprintf(final Args arg) {
        this.args = arg;
    }

    @Override
    @SuppressWarnings("PMD.SystemPrintln")
    public Object call() throws Exception {
        final Collection<Object> items = new LinkedList<>();
        for (final String key : new Sorted<>(this.args.keys())) {
            if (key.charAt(0) != '0') {
                continue;
            }
            if ("01".equals(key)) {
                continue;
            }
            items.add(this.args.call(key, Object.class));
        }
        return String.format(
            this.args.call("01", String.class),
            items.toArray()
        );
    }
}
