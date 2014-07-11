package com.golaszewski.lava.function;

import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Implementation of the LISP 'cdr' function.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class CdrFunction extends Function {

  @Override
  public Expression call(ListExpression args, Environment env) {
    Expression arg = args.first();

    if (!(arg instanceof ListExpression)) {
      throw new IllegalArgumentException(
          String.format("cdr: the input must be a list or pair! got: %s", arg));
    }

    ListExpression asList = (ListExpression) arg;
    return asList.rest();
  }

  @Override
  public boolean evaluateArguments() {
    return true;
  }

}
