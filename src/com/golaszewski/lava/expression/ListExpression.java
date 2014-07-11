package com.golaszewski.lava.expression;

import com.golaszewski.lava.atom.FunctionAtom;
import com.golaszewski.lava.atom.LambdaAtom;

/**
 * Expression consisting of two elements: first, the head of the list. second,
 * the rest of the list.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class ListExpression implements Expression {
  private Expression first;
  private Expression rest;

  /**
   * Creates a new ListExpression.
   * 
   * @param first, the head of the list. Can be an atom or a list.
   * @param rest, the tail of the list. Can be an atom or a list.
   */
  public ListExpression(Expression first, Expression rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public String toString() {
    return toString(true);
  }

  public String toString(boolean isHead) {
    if (isHead) {
      if (null == rest) {
        return String.format("(%s)", first);
      } else if (rest instanceof AtomicExpression) {
        return String.format("(%s . %s)", first, rest);
      } else {
        return String.format("(%s %s", first, rest.toString(false));
      }
    } else {
      if (null == rest) {
        return String.format("%s)", first);
      } else if (rest instanceof AtomicExpression) {
        return String.format("%s . %s)", first, rest);
      } else {
        return String.format("%s %s", first, rest.toString(false));
      }
    }
  }

  /**
   * Returns the head of the list.
   * 
   * @return the list head.
   */
  public Expression first() {
    return first;
  }

  /**
   * Returns the tail of the list.
   * 
   * @return the list tail.
   */
  public Expression rest() {
    return rest;
  }

  /**
   * Sets the head of the list.
   * 
   * @param first, the head of the list.
   */
  public void setFirst(Expression first) {
    this.first = first;
  }

  /**
   * Sets the rest of the list.
   * 
   * @param rest the rest of the list.
   */
  public void setRest(Expression rest) {
    this.rest = rest;
  }

  @Override
  public Expression evaluate(Environment env) {
    AtomicExpression head = (AtomicExpression) first.evaluate(env);
    ListExpression args = (ListExpression) rest;

    if (head.getAtom() instanceof FunctionAtom) {
      return functionCall(head, args, env);
    } else if (head.getAtom() instanceof LambdaAtom) {
      return lambdaCall(head, args, env);
    }

    return null;
  }

  /**
   * Performs a function call.
   * 
   * @param head, reference to the atomic expression that contains the function
   *        to call.
   * @param args, the head of the argument list.
   * @param env, the execution environment.
   * @return the result of the call.
   */
  private Expression functionCall(AtomicExpression head, ListExpression args,
      Environment env) {

    FunctionAtom function = (FunctionAtom) head.getAtom();

    if (function.evaluateArguments()) {
      args = evaluateArgs(args, env);
    }

    return function.call(args, env);
  }

  /**
   * Evaluates a list of arguments.
   * 
   * @param args, the argument list head.
   * @param env, the execution environment.
   * @return the argument list with the arguments evaluated.
   */
  private ListExpression evaluateArgs(ListExpression args, Environment env) {
    ListExpression result;
    ListExpression evaluated = new ListExpression(null, null);
    ListExpression current = args;

    /* Capture the head of the evaluated list so we don't lose the reference. */
    result = evaluated;

    // We need to create a copy of the args, otherwise we mess up lambdas real
    // bad.
    while (current instanceof ListExpression) {
      evaluated.first = current.first.evaluate(env);

      if (current.rest != null) {
        ListExpression next = new ListExpression(null, null);
        evaluated.rest = next;
        evaluated = next;
      }

      current = (ListExpression) current.rest;
    }

    return result;
  }

  /**
   * Performs a lambda call.
   * 
   * @param head, the wrapped lambda atom.
   * @param args, the arguments for the lamba call.
   * @param env, the execution environment.
   * @return the evaluation of the lambda.
   */
  private Expression lambdaCall(AtomicExpression head, ListExpression args,
      Environment env) {
    LambdaAtom lambda = (LambdaAtom) head.getAtom();
    args = evaluateArgs(args, env);
    return lambda.invoke(args, env);
  }
}
