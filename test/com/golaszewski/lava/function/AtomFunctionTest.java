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
 * Test cases for the AtomFunction class.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class AtomFunctionTest {
  private AtomFunction atomFunction = new AtomFunction();
  private Environment testEnv = new Environment();
  
  @Test
  public void passAtom() {
    ListExpression arg = Util.generateList("A");
    AtomicExpression result = (AtomicExpression) atomFunction.call(arg, testEnv);
    assertTrue(result.getAtom().equals(TrueAtom.getInstance()));
  }
  
  @Test
  public void passList() {
    ListExpression list = Util.generateList("A", "B");
    ListExpression arg = Util.generateExpression(list);
    AtomicExpression result = (AtomicExpression) atomFunction.call(arg, testEnv);
    assertTrue(result.getAtom().equals(NilAtom.getInstance()));
  }
}
