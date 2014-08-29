package com.golaszewski.lava.evaluate;

import java.util.Queue;
import com.golaszewski.lava.atom.RawAtom;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;
import com.golaszewski.lava.tokenizer.Token;
import com.golaszewski.lava.tokenizer.TokenType;

/**
 * Parser for parsing s-expressions out of a token stream.
 */
public class ExpressionParser
{

    /**
     * Will parse a single s-expression or atom out of the token stream. This will pop tokens until an s-expression or
     * an atom has been built.
     * 
     * If an opening parenthesis is encountered for an s-expression, parsing will continue until a closing parenthesis
     * is found. If the end of input is reached without encountering one, an error will be thrown.
     * 
     * @param tokenStream , the stream to pop tokens from. Only one s-expression worth of tokens will be popped off this
     *            stream per call.
     * @return an s-expression built out of tokens in the stream.
     * @throws IllegalStateException , if unexpected tokens are encountered while parsing.
     */
    public Expression parse(Queue<Token> tokenStream)
    {
        Token t = tokenStream.poll();

        switch (t.getType())
        {
            case LEFT_PARENTHESIS:
                return parseSExpression(tokenStream);
            case RIGHT_PARENTHESIS:
                throw new IllegalStateException("Encountered unexpected right parenthesis in input.");
            default:
                return new AtomicExpression(new RawAtom(t.getText()));
        }
    }

    /**
     * Parses an s-expression out of the token stream.
     * 
     * @param tokenStream, the token stream to pop tokens from.
     * @return an s-expression.
     * @throws IllegalStateException , if the token stream is emptied without finding a closing parenthesis.
     */
    private Expression parseSExpression(Queue<Token> tokenStream)
    {
        ListExpression head = new ListExpression(parse(tokenStream), null);
        ListExpression prev = head;

        /*
         * Continue popping/parsing tokens and building the expression until a right parenthesis is reached - the right
         * parenthesis indicates the end of the s-expression.
         */
        while (tokenStream.peek().getType() != TokenType.RIGHT_PARENTHESIS)
        {
            ListExpression next = new ListExpression(parse(tokenStream), null);
            prev.setRest(next);
            prev = next;

            if (tokenStream.peek() == null)
            {
                throw new IllegalStateException(String.format("%s - missing closing parenthesis!", head));
            }
        }

        // Remove the closing right parenthesis from the queue.
        tokenStream.poll();
        return head;
    }
}
