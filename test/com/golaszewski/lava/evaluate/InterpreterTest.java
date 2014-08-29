package com.golaszewski.lava.evaluate;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.golaszewski.lava.expression.Expression;

/**
 * Test suite for the top level interpreter.
 * 
 * @see com.golaszewski.lava.evaluate.REPL
 * 
 * @author Ennis Golaszewski
 * 
 */
public class InterpreterTest {

  private Interpreter interpreter;

  @Before
  public void initInterpreter() {
    interpreter = new Interpreter();
  }

  @Test
  public void cond() {
    System.out.println("... Cond Tests ...");
    makeAssertion("(cond (nil (quote B)) (#t (quote A)))", "A");
    makeAssertion(
        "(cond ((eq (quote A) (quote B)) (quote A)) ((eq (quote A) (quote A)) (quote A)))",
        "A");
  }

  @Test
  public void eq() {
    System.out.println("... Eq Tests ...");
    makeAssertion("(eq (quote A) (quote B))", "nil");
    makeAssertion("(eq (quote A) (quote A))", "#t");
    makeAssertion("(eq (cons (quote A) (quote B)) (cons (quote A) (quote B)))",
        "nil");
    makeAssertion("(eq (cons (quote A) (quote B)) (cons (quote B) (quote C)))",
        "nil");
  }

  @Test
  public void lambda() {
    System.out.println("... Lambda Tests ...");
    makeAssertion("(label null? (lambda (x) (eq x nil)))", "#t");
    makeAssertion("(null? nil)", "#t");
    makeAssertion("(null? (quote A))", "nil");
    makeAssertion("(null? (quote (A B C)))", "nil");
    makeAssertion("(null? null?)", "nil");
  }

  @Test
  public void atom() {
    System.out.println("... Atom Tests ...");
    makeAssertion("(atom (quote A))", "#t");
    makeAssertion("(atom (quote (A B)))", "nil");
  }
  
  @Test
  public void buildList() {
    System.out.println("... Building a List ...");
    makeAssertion("(cons (quote a) (cons (quote b) (cons (quote c) (cons (quote d) nil))))", "(a b c d . nil)");
  }

  @Test
  public void recursiveFunction() {
    System.out.println("... Recursive Function Test ...");
    makeAssertion(
        "(label last (lambda (list) (cond ((eq nil (cdr list)) (car list)) (#t (last (cdr list))))))",
        "#t");
    makeAssertion(
        "(last (cons (quote a) (cons (quote b) (cons (quote c) nil))))",
        "c");
  }

  private void makeAssertion(String expression, String expected) {
    Expression result = interpreter.interpretString(expression);
    System.out.printf("%s -> %s\n", expression, result);
    assertTrue(result.toString().equals(expected));
  }
}
