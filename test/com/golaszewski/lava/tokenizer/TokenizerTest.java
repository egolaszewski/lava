package com.golaszewski.lava.tokenizer;

import static org.junit.Assert.assertTrue;
import java.util.Queue;
import org.junit.Test;
import com.golaszewski.lava.tokenizer.Token;
import com.golaszewski.lava.tokenizer.TokenType;
import com.golaszewski.lava.tokenizer.Tokenizer;

public class TokenizerTest
{
    public static final String TEST = "(+ 2 3)";

    @Test
    public void test()
    {
        Tokenizer tokenizer = new Tokenizer();

        Queue<Token> queue = tokenizer.tokenize(TEST);
        assertTrue(queue != null);
        assertTrue(queue.size() == 5);

        Token[] tokens = new Token[queue.size()];
        tokens = queue.toArray(tokens);

        Token leftParen = new Token(TokenType.LEFT_PARENTHESIS, "(", 0, 0);
        Token plus = new Token(TokenType.IDENTIFIER, "+", 0, 1);
        Token two = new Token(TokenType.NUMBER, "2", 0, 3);
        Token three = new Token(TokenType.NUMBER, "3", 0, 5);
        Token rightParen = new Token(TokenType.RIGHT_PARENTHESIS, ")", 0, 6);

        assertTrue(tokens[0].equals(leftParen));
        assertTrue(tokens[1].equals(plus));
        assertTrue(tokens[2].equals(two));
        assertTrue(tokens[3].equals(three));
        assertTrue(tokens[4].equals(rightParen));
    }
}
