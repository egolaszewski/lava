package com.golaszewski.lava.function;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.golaszewski.lava.atom.LambdaAtom;
import com.golaszewski.lava.evaluate.Util;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Test cases for the LambdaFunction object.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class LambdaFunctionTest {
  private LambdaFunction lambdaFunction = new LambdaFunction();
  private Environment testEnv = new Environment();
  
  @Test
  public void lambda() {
    ListExpression args = Util.generateList("x", "y");
    ListExpression body = Util.generateList("cons", "x", "y");
    ListExpression functArgs = Util.generateExpression(args, body);
    
    AtomicExpression result = (AtomicExpression) lambdaFunction.call(functArgs, testEnv);
    LambdaAtom lambda = (LambdaAtom) result.getAtom();
    
    assertTrue(lambda.getArgs().equals(args));
    assertTrue(lambda.getBody().equals(body));
  }
  
}
