package com.golaszewski.lava.tokenizer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Provides a context for a run of the tokenizer.
 * 
 * The context holds row/column information on the token being parsed as well as a buffer for
 * building a token and a queue of tokens that have been parsed.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class TokenizerContext {
  private int row;
  private int column;
  private TokenizerState state;
  private StringBuilder buffer;
  private Queue<Token> tokenQueue;

  /**
   * Creates a new TokenizerContext. This context is completely clean and ready to use for a
   * tokenizer run.
   */
  public TokenizerContext() {
    row = 0;
    column = 0;
    state = TokenizerState.READY;
    buffer = new StringBuilder();
    tokenQueue = new LinkedList<Token>();
  }

  /**
   * Returns the row of the current token being built.
   * 
   * @return row.
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the column of the current token being built.
   * 
   * @return column.
   */
  public int getColumn() {
    return column;
  }

  /**
   * Sets the row of the token being built in the context.
   * 
   * @param row, the row to set.
   */
  public void setRow(int row) {
    this.row = row;
  }

  /**
   * Sets the column of the token being built in the context.
   * 
   * @param column, the column to set.
   */
  public void setColumn(int column) {
    this.column = column;
  }

  /**
   * Pops the token in the buffer and places it into the internal token queue. This has the side
   * effect of resetting the context state to 'READY'.
   */
  public void popToken() {
    Token token = null;

    // TODO remove token types and make this a function call to the Token class instead.
    switch (state) {
      case NUMBER:
        token = new Token(TokenType.NUMBER, buffer.toString(), row, column);
        break;
      case IDENTIFIER:
        token = new Token(TokenType.IDENTIFIER, buffer.toString(), row, column);
        break;
      case LEFT_PARENTHESIS:
        token = new Token(TokenType.LEFT_PARENTHESIS, buffer.toString(), row, column);
        break;
      case RIGHT_PARENTHESIS:
        token = new Token(TokenType.RIGHT_PARENTHESIS, buffer.toString(), row, column);
        break;
      default:
        // TODO error.
        break;
    }

    if (token != null) {
      tokenQueue.add(token);
    }

    resetState();
  }

  /**
   * Pushes a character onto the token buffer.
   * 
   * @param c, the character to push.
   */
  public void pushCharacter(char c) {
    buffer.append(c);
  }

  /**
   * Returns the queue of accumulated tokens.
   * 
   * @return tokens, the token queue. See java.util.Queue.
   */
  public Queue<Token> getQueue() {
    return tokenQueue;
  }

  /**
   * Returns the context state.
   * 
   * @return the state.
   */
  public TokenizerState getState() {
    return state;
  }

  /**
   * Sets the context state.
   * 
   * @param state, the tokenizer state to set.
   */
  public void setState(TokenizerState state) {
    this.state = state;
  }

  private void resetState() {
    state = TokenizerState.READY;
    buffer = new StringBuilder();
  }
}
