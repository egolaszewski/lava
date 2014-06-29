package com.golaszewski.lava.function;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.golaszewski.lava.evaluate.Util;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Test cases for the QuoteFunction object.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class QuoteFunctionTest {
  private QuoteFunction quoteFunction = new QuoteFunction();
  private Environment testEnv = new Environment();

  @Test
  public void quoteAtom() {
    ListExpression args = Util.generateList("A");
    
    Expression result = quoteFunction.call(args, testEnv);
    assertTrue(result.equals(args.first()));
  }

  @Test
  public void quotePair() {
    ListExpression args = Util.generatePair("A", "B");
    ListExpression argsHead = new ListExpression(args, Util.createNil());

    Expression result = quoteFunction.call(argsHead, testEnv);
    assertTrue(result.equals(args));
  }

  @Test
  public void quoteList() {
    ListExpression args = Util.generateList("A", "B", "C", "D", "E");
    ListExpression argsHead = new ListExpression(args, Util.createNil());
    
    Expression result = quoteFunction.call(argsHead, testEnv);
    assertTrue(result.equals(args));
  }
  
}
