package com.golaszewski.lava.evaluate;

import java.util.Queue;
import java.util.Scanner;

import com.golaszewski.lava.atom.RawAtom;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;
import com.golaszewski.lava.tokenizer.Token;
import com.golaszewski.lava.tokenizer.TokenType;
import com.golaszewski.lava.tokenizer.Tokenizer;

/**
 * Interpreter for LISP s-expressions.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class Interpreter {

  public Environment env = new Environment();
  private Tokenizer tokenizer = new Tokenizer();

  public static void main(String[] args) {
    Interpreter interpreter = new Interpreter();
    Scanner sc = new Scanner(System.in);

    boolean running = true;

    while (running) {
      String input = sc.nextLine();
      if (input.equals("(quit)")) {
        running = false;
      } else {
        System.out.println(interpreter.interpret(input));
      }
    }

    sc.close();
  }

  /**
   * Interprets the input string.
   * 
   * @param input, the input to to interpret.
   * @return the outcome of evaluation.
   */
  public Expression interpret(String input) {
    return interpret(parse(tokenizer.tokenize(input)));
  }

  /**
   * Interprets the input.
   * 
   * @param e, the input to interpret.
   * @return the result of evaluating the input.
   */
  public Expression interpret(Expression e) {
    return e.evaluate(env);
  }

  private Expression parse(Queue<Token> tokenQueue) {
    return parseExpression(tokenQueue);
  }

  private Expression parseExpression(Queue<Token> tokenQueue) {
    Expression parsed;
    Token token = tokenQueue.poll();

    if (token.getType() == TokenType.LEFT_PARENTHESIS) {

      ListExpression head =
          new ListExpression(parseExpression(tokenQueue), null);

      ListExpression prev = head;

      while (tokenQueue.peek().getType() != TokenType.RIGHT_PARENTHESIS) {

        ListExpression next =
            new ListExpression(parseExpression(tokenQueue), null);

        prev.setRest(next);
        prev = next;

        if (tokenQueue.peek() == null) {
          throw new IllegalArgumentException(String.format(
              "%s - missing closing parenthesis!", head));
        }
      }

      parsed = head;
      tokenQueue.poll();
    } else {
      String value = token.getText();
      parsed = new AtomicExpression(new RawAtom(value));
    }

    return parsed;
  }
}
