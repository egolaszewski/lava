package com.golaszewski.lava.evaluate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;

import com.golaszewski.lava.atom.RawAtom;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;
import com.golaszewski.lava.tokenizer.Token;
import com.golaszewski.lava.tokenizer.TokenType;
import com.golaszewski.lava.tokenizer.Tokenizer;

/**
 * Interpreter for LISP source files.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class FileInterpreter {

  private Environment env;
  private Tokenizer tok;

  public FileInterpreter() {
    env = new Environment();
    tok = new Tokenizer();
  }

  public static void main(String[] args) {
    FileInterpreter interpreter = new FileInterpreter();
    interpreter.interpret(new File("interpreter.lisp"));
  }
  
  public Expression interpretString(String s) {
    Queue<Token> tokens = tok.tokenize(s);
    Expression e = parseExpression(tokens);
    return e.evaluate(env);
  }

  /**
   * Interprets the input file.
   * 
   * @param sourceFile, file containing LISP source code.
   */
  public void interpret(File sourceFile) {
    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new FileReader(sourceFile));
      String input = readInput(reader);
      Queue<Token> tokens = tok.tokenize(input);

      while (tokens.size() > 0) {
        Expression e = parseExpression(tokens);
        Expression r = e.evaluate(env);
        System.out.printf("%s -> %s\n", e, r);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeReader(reader);
    }
  }

  /**
   * Reads input from a file and concatenates it into a single string.
   * 
   * @param reader, the reader for the file.
   * @return a single string of the file characters.
   * @throws IOException, if there is an issue reading.
   */
  private String readInput(BufferedReader reader) throws IOException {
    StringBuilder inputBuilder = new StringBuilder();

    while (reader.ready()) {
      String line = reader.readLine();
      inputBuilder.append(line);
      inputBuilder.append(System.lineSeparator());
    }

    return inputBuilder.toString();
  }

  /**
   * Closes the input reader, swallowing exceptions in the process.
   * 
   * @param reader, the buffered reader to close.
   */
  private void closeReader(BufferedReader reader) {
    try {
      reader.close();
    } catch (IOException e) {
      // TODO
    } catch (NullPointerException e) {
      // TODO
    }
  }

  /**
   * Parses s-expressions out of a token stream.
   * 
   * @param tokenQueue, the queue of tokens providing the token stream.
   * @return the first parsed s-expression.
   */
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
