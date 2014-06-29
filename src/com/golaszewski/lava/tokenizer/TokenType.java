package com.golaszewski.lava.tokenizer;

/**
 * Specifications of token types as output by the tokenizer.
 * 
 * @author Ennis Golaszewski
 * 
 */
public enum TokenType {
  /**
   * Anything that isn't a number (starts with a digit) is classified as an identifier.
   */
  IDENTIFIER,

  /**
   * Numbers are numbers. (i.e. 1234, 13.5, etc.)
   */
  NUMBER,

  /**
   * Left parenthesis open s-expressions.
   */
  LEFT_PARENTHESIS,

  /**
   * Right parenthesis close s-expressions.
   */
  RIGHT_PARENTHESIS
}
