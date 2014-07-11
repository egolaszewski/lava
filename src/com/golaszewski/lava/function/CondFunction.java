package com.golaszewski.lava.function;

import com.golaszewski.lava.atom.TrueAtom;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Implementation of the LISP 'cond' function.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class CondFunction extends Function {

  @Override
  public Expression call(ListExpression args, Environment env) {
    Expression result = null;
    int numClauses = getArgCount(args);

    for (int i = 0; i < numClauses; i++) {
      ListExpression clause = (ListExpression) getArgument(args, i);
      result = evaluateClause(clause, env);

      if (result != null) {
        break;
      }
    }
    
    // TODO insert guard if condition does not evaluate to nil or #t
    if (result == null) {
      throw new RuntimeException("YOU SHALL NOT PASS!");
    }
    
    return result;
  }

  @Override
  public boolean evaluateArguments() {
    return false;
  }

  private Expression evaluateClause(ListExpression clause, Environment env) {
    Expression condition = getArgument(clause, 0);
    Expression body = getArgument(clause, 1);
    Expression evaluated = null;

    Expression conditionResult = condition.evaluate(env);

    if (isTrue(conditionResult)) {
      evaluated = body.evaluate(env);
    }

    return evaluated;
  }

  /**
   * Returns true if the input expression is an atomic expression carrying a
   * True atom.
   * 
   * @param expr, the expression to check.
   * @return true or false.
   */
  private boolean isTrue(Expression expr) {
    boolean result = false;

    if (expr instanceof AtomicExpression) {
      AtomicExpression atomExpr = (AtomicExpression) expr;

      if (atomExpr.getAtom() instanceof TrueAtom) {
        result = true;
      }
    }

    return result;
  }

}
