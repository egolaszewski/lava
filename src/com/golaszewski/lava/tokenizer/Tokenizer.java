package com.golaszewski.lava.tokenizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * @author Ennis Golaszewski
 */
public class Tokenizer
{
    private int row;
    private int column;
    private Processor[][] stateTable;

    /**
     * Classifications for different types of characters. These will allow the tokenizing state machine to make
     * decisions on which state/action to pursue based on the current character.
     */
    private enum Classification
    {
        ALPHABETIC, DIGIT, LEFT_PARENTHESES, RIGHT_PARENTHESES, SPACE, NEW_LINE, OTHER;

        /**
         * Classifies the input character, matching it to one of the enumerations.
         * 
         * @param c, the character to classify.
         * @return the most appropriate enumeration in the Classification enum.
         */
        public static Classification classify(char c)
        {
            Classification result;
            c = Character.toLowerCase(c);

            if ('a' < c && c < 'z')
            {
                result = Classification.ALPHABETIC;
            }
            else if ('0' < c && c < '9')
            {
                result = Classification.DIGIT;
            }
            else if (c == '(')
            {
                result = Classification.LEFT_PARENTHESES;
            }
            else if (c == ')')
            {
                result = Classification.RIGHT_PARENTHESES;
            }
            else if (c == '\n' || c == '\r')
            {
                result = Classification.NEW_LINE;
            }
            else if (c == ' ' || c == '\t')
            {
                result = Classification.SPACE;
            }
            else
            {
                result = Classification.OTHER;
            }

            return result;
        }
    }

    /**
     * Provides a base class for the processor objects - these are wrappers for a processing function. The function in
     * question resides in a state machine table.
     */
    private abstract class Processor
    {
        public abstract void process(char c, TokenizerContext context);

        public void updateContextPosition(TokenizerContext context)
        {
            if (context.getState() == TokenizerState.READY)
            {
                context.setRow(row);
                context.setColumn(column);
            }
        }
    }

    /**
     * Used to process/build identifiers.
     */
    private class IdentifierProcessor extends Processor
    {

        @Override
        public void process(char c, TokenizerContext context)
        {
            updateContextPosition(context);
            context.setState(TokenizerState.IDENTIFIER);
            context.pushCharacter(c);
            column += 1;
        }
    }

    /**
     * Used to process/build numbers.
     */
    private class NumberProcessor extends Processor
    {

        @Override
        public void process(char c, TokenizerContext context)
        {
            updateContextPosition(context);
            context.setState(TokenizerState.NUMBER);
            context.pushCharacter(c);
            column += 1;
        }
    }

    /**
     * Used to process left parenthesis.
     */
    private class LeftParenthesisProcessor extends Processor
    {

        @Override
        public void process(char c, TokenizerContext context)
        {
            updateContextPosition(context);
            context.setState(TokenizerState.LEFT_PARENTHESIS);
            context.pushCharacter(c);
            context.popToken();
            column += 1;
        }
    }

    /**
     * Used to process right parenthesis.
     */
    private class RightParenthesisProcessor extends Processor
    {

        @Override
        public void process(char c, TokenizerContext context)
        {
            if (context.getState() != TokenizerState.READY)
            {
                context.popToken();
            }

            updateContextPosition(context);
            context.setState(TokenizerState.RIGHT_PARENTHESIS);
            context.pushCharacter(c);
            context.popToken();
            column += 1;
        }
    }

    /**
     * Used to process a blank space.
     */
    private class SpaceProcessor extends Processor
    {

        @Override
        public void process(char c, TokenizerContext context)
        {
            if (context.getState() != TokenizerState.READY)
            {
                context.popToken();
            }

            column += 1;
        }
    }

    /**
     * Used to process a new line.
     */
    private class NewLineProcessor extends Processor
    {

        @Override
        public void process(char c, TokenizerContext context)
        {
            if (context.getState() != TokenizerState.READY)
            {
                context.popToken();
            }

            column = 0;
            row += 1;
        }
    }

    private class ErrorProcessor extends Processor
    {

        @Override
        public void process(char c, TokenizerContext context)
        {
            throw new RuntimeException(String.format("Unexpected character encountered [row %d, col %d, state %s]: %s",
                    context.getRow(), context.getColumn(), context.getState(), c));
        }
    }

    private static Map<Classification, Integer> classificationIndexes = new HashMap<Classification, Integer>();

    private static Map<TokenizerState, Integer> stateIndexes = new HashMap<TokenizerState, Integer>();

    /*
     * In order to quickly determine the next state, we construct a 2D array of state transitions. To ensure easy access
     * by indexes, the row/column enumerations need to be mapped to integer indecies.
     */
    static
    {
        classificationIndexes.put(Classification.ALPHABETIC, 0);
        classificationIndexes.put(Classification.DIGIT, 1);
        classificationIndexes.put(Classification.LEFT_PARENTHESES, 2);
        classificationIndexes.put(Classification.RIGHT_PARENTHESES, 3);
        classificationIndexes.put(Classification.SPACE, 4);
        classificationIndexes.put(Classification.NEW_LINE, 5);
        classificationIndexes.put(Classification.OTHER, 6);

        stateIndexes.put(TokenizerState.READY, 0);
        stateIndexes.put(TokenizerState.IDENTIFIER, 1);
        stateIndexes.put(TokenizerState.NUMBER, 2);
    }

    /**
     * Initializes the state table. This depends on the indexes being statically configured.
     */
    private void initializeStateTable()
    {
        Processor identifier = new IdentifierProcessor();
        Processor number = new NumberProcessor();
        Processor lParen = new LeftParenthesisProcessor();
        Processor rParen = new RightParenthesisProcessor();
        Processor space = new SpaceProcessor();
        Processor newLine = new NewLineProcessor();
        Processor error = new ErrorProcessor();

        // @formatter:off
        stateTable = new Processor[][]
        {
        /* READY | IDENTIFIER | NUMBER */
        { identifier, identifier, error }, /* ALPHABETIC */
        { number, identifier, number }, /* DIGIT */
        { lParen, error, error }, /* L_PAREN */
        { rParen, rParen, rParen }, /* R_PAREN */
        { space, space, space }, /* SPACE */
        { newLine, newLine, newLine }, /* NEW_LINE */
        { identifier, identifier, error } /* OTHER */
        };
        // @formatter:on

    }

    /**
     * Tokenizes the input.
     * 
     * @param input, a string. This should be an s-expression.
     * @return list of tokens in the input.
     */
    public Queue<Token> tokenize(String input)
    {
        TokenizerContext context = new TokenizerContext();
        initializeStateTable();

        for (char c : input.toCharArray())
        {
            Classification classification = Classification.classify(c);
            TokenizerState state = context.getState();
            int tableColumn = stateIndexes.get(state);
            int tableRow = classificationIndexes.get(classification);
            Processor processor = stateTable[tableRow][tableColumn];
            processor.process(c, context);
        }

        // To handle a case of a single symbol not contained by a list, we need to
        // pop anything left after parsing.
        context.popToken();

        return context.getQueue();
    }
}
