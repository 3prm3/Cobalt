package org.luaj.vm2;

import org.junit.Before;
import org.junit.Test;
import org.luaj.vm2.lib.MathLib;
import org.luaj.vm2.lib.jse.JsePlatform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.luaj.vm2.Factory.valueOf;

public class MathLibTest {
	private LuaValue j2se;
	private LuaValue j2me;
	private boolean supportedOnJ2me;

	public MathLibTest() {
		LuaValue g = JsePlatform.standardGlobals();
		j2se = g.get("math");
		g.load(new MathLib());
		j2me = g.get("math");
	}

	@Before
	public void setUp() throws Exception {
		supportedOnJ2me = true;
	}

	@Test
	public void testMathDPow() {
		assertEquals(1, j2mepow(2, 0), 0);
		assertEquals(2, j2mepow(2, 1), 0);
		assertEquals(8, j2mepow(2, 3), 0);
		assertEquals(-8, j2mepow(-2, 3), 0);
		assertEquals(1 / 8., j2mepow(2, -3), 0);
		assertEquals(-1 / 8., j2mepow(-2, -3), 0);
		assertEquals(16, j2mepow(256, .5), 0);
		assertEquals(4, j2mepow(256, .25), 0);
		assertEquals(64, j2mepow(256, .75), 0);
		assertEquals(1. / 16, j2mepow(256, -.5), 0);
		assertEquals(1. / 4, j2mepow(256, -.25), 0);
		assertEquals(1. / 64, j2mepow(256, -.75), 0);
		assertEquals(Double.NaN, j2mepow(-256, .5), 0);
		assertEquals(1, j2mepow(.5, 0), 0);
		assertEquals(.5, j2mepow(.5, 1), 0);
		assertEquals(.125, j2mepow(.5, 3), 0);
		assertEquals(2, j2mepow(.5, -1), 0);
		assertEquals(8, j2mepow(.5, -3), 0);
		assertEquals(1, j2mepow(0.0625, 0), 0);
		assertEquals(0.00048828125, j2mepow(0.0625, 2.75), 0);
	}

	private double j2mepow(double x, double y) {
		return j2me.get("pow").call(valueOf(x), valueOf(y)).todouble();
	}

	@Test
	public void testAbs() {
		tryMathOp("abs", 23.45);
		tryMathOp("abs", -23.45);
	}

	@Test
	public void testCos() {
		tryTrigOps("cos");
	}

	@Test
	public void testCosh() {
		supportedOnJ2me = false;
		tryTrigOps("cosh");
	}

	@Test
	public void testDeg() {
		tryTrigOps("deg");
	}

	@Test
	public void testExp() {
		//supportedOnJ2me = false;
		tryMathOp("exp", 0);
		tryMathOp("exp", 0.1);
		tryMathOp("exp", .9);
		tryMathOp("exp", 1.);
		tryMathOp("exp", 9);
		tryMathOp("exp", -.1);
		tryMathOp("exp", -.9);
		tryMathOp("exp", -1.);
		tryMathOp("exp", -9);
	}

	@Test
	public void testLog() {
		supportedOnJ2me = false;
		tryMathOp("log", 0.1);
		tryMathOp("log", .9);
		tryMathOp("log", 1.);
		tryMathOp("log", 9);
		tryMathOp("log", -.1);
		tryMathOp("log", -.9);
		tryMathOp("log", -1.);
		tryMathOp("log", -9);
	}

	@Test
	public void testLog10() {
		supportedOnJ2me = false;
		tryMathOp("log10", 0.1);
		tryMathOp("log10", .9);
		tryMathOp("log10", 1.);
		tryMathOp("log10", 9);
		tryMathOp("log10", 10);
		tryMathOp("log10", 100);
		tryMathOp("log10", -.1);
		tryMathOp("log10", -.9);
		tryMathOp("log10", -1.);
		tryMathOp("log10", -9);
		tryMathOp("log10", -10);
		tryMathOp("log10", -100);
	}

	@Test
	public void testRad() {
		tryMathOp("rad", 0);
		tryMathOp("rad", 0.1);
		tryMathOp("rad", .9);
		tryMathOp("rad", 1.);
		tryMathOp("rad", 9);
		tryMathOp("rad", 10);
		tryMathOp("rad", 100);
		tryMathOp("rad", -.1);
		tryMathOp("rad", -.9);
		tryMathOp("rad", -1.);
		tryMathOp("rad", -9);
		tryMathOp("rad", -10);
		tryMathOp("rad", -100);
	}

	@Test
	public void testSin() {
		tryTrigOps("sin");
	}

	@Test
	public void testSinh() {
		supportedOnJ2me = false;
		tryTrigOps("sinh");
	}

	@Test
	public void testSqrt() {
		tryMathOp("sqrt", 0);
		tryMathOp("sqrt", 0.1);
		tryMathOp("sqrt", .9);
		tryMathOp("sqrt", 1.);
		tryMathOp("sqrt", 9);
		tryMathOp("sqrt", 10);
		tryMathOp("sqrt", 100);
	}

	@Test
	public void testTan() {
		tryTrigOps("tan");
	}

	@Test
	public void testTanh() {
		supportedOnJ2me = false;
		tryTrigOps("tanh");
	}

	@Test
	public void testAtan2() {
		supportedOnJ2me = false;
		tryDoubleOps("atan2", false);
	}

	@Test
	public void testFmod() {
		tryDoubleOps("fmod", false);
	}

	@Test
	public void testPow() {
		tryDoubleOps("pow", true);
	}

	private void tryDoubleOps(String op, boolean positiveOnly) {
		// y>0, x>0
		tryMathOp(op, 0.1, 4.0);
		tryMathOp(op, .9, 4.0);
		tryMathOp(op, 1., 4.0);
		tryMathOp(op, 9, 4.0);
		tryMathOp(op, 10, 4.0);
		tryMathOp(op, 100, 4.0);

		// y>0, x<0
		tryMathOp(op, 0.1, -4.0);
		tryMathOp(op, .9, -4.0);
		tryMathOp(op, 1., -4.0);
		tryMathOp(op, 9, -4.0);
		tryMathOp(op, 10, -4.0);
		tryMathOp(op, 100, -4.0);

		if (!positiveOnly) {
			// y<0, x>0
			tryMathOp(op, -0.1, 4.0);
			tryMathOp(op, -.9, 4.0);
			tryMathOp(op, -1., 4.0);
			tryMathOp(op, -9, 4.0);
			tryMathOp(op, -10, 4.0);
			tryMathOp(op, -100, 4.0);

			// y<0, x<0
			tryMathOp(op, -0.1, -4.0);
			tryMathOp(op, -.9, -4.0);
			tryMathOp(op, -1., -4.0);
			tryMathOp(op, -9, -4.0);
			tryMathOp(op, -10, -4.0);
			tryMathOp(op, -100, -4.0);
		}

		// degenerate cases
		tryMathOp(op, 0, 1);
		tryMathOp(op, 1, 0);
		tryMathOp(op, -1, 0);
		tryMathOp(op, 0, -1);
		tryMathOp(op, 0, 0);
	}

	private void tryTrigOps(String op) {
		tryMathOp(op, 0);
		tryMathOp(op, Math.PI / 8);
		tryMathOp(op, Math.PI * 7 / 8);
		tryMathOp(op, Math.PI * 8 / 8);
		tryMathOp(op, Math.PI * 9 / 8);
		tryMathOp(op, -Math.PI / 8);
		tryMathOp(op, -Math.PI * 7 / 8);
		tryMathOp(op, -Math.PI * 8 / 8);
		tryMathOp(op, -Math.PI * 9 / 8);
	}

	private void tryMathOp(String op, double x) {
		try {
			double expected = j2se.get(op).call(valueOf(x)).todouble();
			double actual = j2me.get(op).call(valueOf(x)).todouble();
			if (supportedOnJ2me) {
				assertEquals(expected, actual, 1.e-4);
			} else {
				fail("j2me should throw exception for math." + op + " but returned " + actual);
			}
		} catch (LuaError lee) {
			if (supportedOnJ2me) {
				throw lee;
			}
		}
	}


	private void tryMathOp(String op, double a, double b) {
		try {
			double expected = j2se.get(op).call(valueOf(a), valueOf(b)).todouble();
			double actual = j2me.get(op).call(valueOf(a), valueOf(b)).todouble();
			if (supportedOnJ2me) {
				assertEquals(expected, actual, 1.e-5);
			} else {
				fail("j2me should throw exception for math." + op + " but returned " + actual);
			}
		} catch (LuaError lee) {
			if (supportedOnJ2me) {
				throw lee;
			}
		}
	}
}
