/**
 * ****************************************************************************
 * Copyright (c) 2009-2011 Luaj.org. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ****************************************************************************
 */
package org.squiddev.cobalt;

import static org.squiddev.cobalt.Constants.TBOOLEAN;

/**
 * Extension of {@link LuaValue} which can hold a Java boolean as its value.
 * <p>
 * These instance are not instantiated directly by clients.
 * Instead, there are exactly two instances of this class,
 * {@link Constants#TRUE} and {@link Constants#FALSE}
 * representing the lua values {@code true} and {@code false}.
 * The function {@link ValueFactory#valueOf(boolean)} will always
 * return one of these two values.
 * <p>
 * Any {@link LuaValue} can be converted to its equivalent
 * boolean representation using {@link LuaValue#toboolean()}
 *
 * @see LuaValue
 * @see ValueFactory#valueOf(boolean)
 * @see Constants#TRUE
 * @see Constants#FALSE
 */
public final class LuaBoolean extends LuaValue {

	/**
	 * The singleton instance representing lua {@code true}
	 */
	static final LuaBoolean _TRUE = new LuaBoolean(true);

	/**
	 * The singleton instance representing lua {@code false}
	 */
	static final LuaBoolean _FALSE = new LuaBoolean(false);

	/**
	 * The value of the boolean
	 */
	public final boolean v;

	LuaBoolean(boolean b) {
		this.v = b;
	}

	@Override
	public int type() {
		return TBOOLEAN;
	}

	@Override
	public String typeName() {
		return "boolean";
	}

	@Override
	public boolean isboolean() {
		return true;
	}

	@Override
	public boolean toboolean() {
		return v;
	}

	@Override
	public String tojstring() {
		return v ? "true" : "false";
	}

	@Override
	public boolean optboolean(boolean defval) {
		return this.v;
	}

	@Override
	public boolean checkboolean() {
		return v;
	}

	@Override
	public LuaValue getMetatable(LuaState state) {
		return state.booleanMetatable;
	}
}
