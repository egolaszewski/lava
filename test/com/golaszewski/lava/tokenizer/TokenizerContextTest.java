package com.golaszewski.lava.tokenizer;

import static org.junit.Assert.assertTrue;

import java.util.Queue;

import org.junit.Test;

import com.golaszewski.lava.tokenizer.Token;
import com.golaszewski.lava.tokenizer.TokenType;
import com.golaszewski.lava.tokenizer.TokenizerContext;
import com.golaszewski.lava.tokenizer.TokenizerState;

/**
 * @author Ennis Golaszewski
 */
public class TokenizerContextTest {

  @Test
  public void test() {
    TokenizerContext context = new TokenizerContext();
    assertTrue(context.getState() == TokenizerState.READY);
    
    context.setState(TokenizerState.IDENTIFIER);
    context.setRow(5);
    context.setColumn(1);
    
    assertTrue(context.getState() == TokenizerState.IDENTIFIER);
    assertTrue(context.getRow() == 5);
    assertTrue(context.getColumn() == 1);
    
    context.pushCharacter('t');
    context.pushCharacter('e');
    context.pushCharacter('s');
    context.pushCharacter('t');
    context.popToken();
    
    assertTrue(context.getState() == TokenizerState.READY);
    
    context.setState(TokenizerState.NUMBER);
    context.setRow(6);
    context.setColumn(1);
    
    assertTrue(context.getState() == TokenizerState.NUMBER);
    assertTrue(context.getRow() == 6);
    assertTrue(context.getColumn() == 1);
    
    context.pushCharacter('4');
    context.pushCharacter('2');
    context.popToken();
    
    assertTrue(context.getState() == TokenizerState.READY);
    
    Queue<Token> tokens = context.getQueue();
    assertTrue(tokens != null);
    assertTrue(tokens.size() == 2);
    
    Token first = tokens.poll();
    assertTrue(first.getType() == TokenType.IDENTIFIER);
    assertTrue(first.getText().equals("test"));
    assertTrue(first.getRow() == 5);
    assertTrue(first.getColumn() == 1);
    
    Token second = tokens.poll();
    assertTrue(second.getType() == TokenType.NUMBER);
    assertTrue(second.getText().equals("42"));
    assertTrue(second.getRow() == 6);
    assertTrue(second.getColumn() == 1);
  }

}
