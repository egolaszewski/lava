package com.golaszewski.lava.function;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.golaszewski.lava.atom.RawAtom;
import com.golaszewski.lava.atom.TrueAtom;
import com.golaszewski.lava.evaluate.Util;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Test cases for the label function.
 * 
 * @author Ennis Golaszewski
 */
public class LabelFunctionTest {
  private LabelFunction labelFunction = new LabelFunction();
  private Environment testEnv = new Environment();
  
  @Test
  public void label() {
    ListExpression args = Util.generateList("true", "#t");
    AtomicExpression result = (AtomicExpression) labelFunction.call(args, testEnv);
    assertTrue(result.getAtom().equals(TrueAtom.getInstance()));
    
    AtomicExpression binding = (AtomicExpression) testEnv.getBinding(new RawAtom("true"));
    assertTrue(binding.getAtom().equals(TrueAtom.getInstance()));
  }
}
