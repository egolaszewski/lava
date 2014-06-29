package com.golaszewski.lava.tokenizer;

/**
 * Specification of states for the tokenizer state machine.
 * 
 * @author Ennis Golaszewski
 * 
 */
public enum TokenizerState {
  /**
   * READY: The tokenizer is ready to begin building a new token.
   */
  READY,

  /**
   * NUMBER: The tokenizer is in the process of tokenizing a number.
   */
  NUMBER,

  /**
   * IDENTIFIER: The tokenizer is in the process of tokenizing an identifier.
   */
  IDENTIFIER,
  
  /**
   * LEFT PARENTHESIS: The tokenizer is in the process of tokenizing a left parenthesis.
   */
  LEFT_PARENTHESIS,
  
  /**
   * RIGHT PARENTHESIS: The tokenizer is in the process of tokenizing a right parenthesis.
   */
  RIGHT_PARENTHESIS
}
