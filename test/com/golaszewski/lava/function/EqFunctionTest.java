package com.golaszewski.lava.function;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.golaszewski.lava.atom.NilAtom;
import com.golaszewski.lava.atom.TrueAtom;
import com.golaszewski.lava.evaluate.Util;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Test cases for the EqFunction class.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class EqFunctionTest {
  private EqFunction eqFunction = new EqFunction();
  private Environment testEnv = new Environment();

  @Test
  public void atomsEqual() {
    ListExpression falseExpr = Util.generateList("nil", "#t");
    ListExpression trueExpr1 = Util.generateList("#t", "#t");
    ListExpression trueExpr2 = Util.generateList("nil", "nil");
    
    AtomicExpression result1 = (AtomicExpression) eqFunction.call(falseExpr, testEnv);
    AtomicExpression result2 = (AtomicExpression) eqFunction.call(trueExpr1, testEnv);
    AtomicExpression result3 = (AtomicExpression) eqFunction.call(trueExpr2, testEnv);
    
    assertTrue(result1.getAtom().equals(NilAtom.getInstance()));
    assertTrue(result2.getAtom().equals(TrueAtom.getInstance()));
    assertTrue(result3.getAtom().equals(TrueAtom.getInstance()));
  }
}
