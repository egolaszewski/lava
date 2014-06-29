package com.golaszewski.lava.function;

import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Implemention of the LISP car function.
 * 
 * @author Ennis Golaszewski
 */
public class CarFunction extends Function {

  @Override
  public Expression call(ListExpression args, Environment env) {
    Expression arg = args.first();

    if (!(arg instanceof ListExpression)) {
      throw new IllegalArgumentException(
          "car: the input must be a list or pair!");
    }

    ListExpression asList = (ListExpression) arg;
    return asList.first();
  }

  @Override
  public boolean evaluateArguments() {
    return true;
  }

}
