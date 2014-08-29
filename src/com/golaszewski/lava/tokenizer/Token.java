package com.golaszewski.lava.tokenizer;

/**
 * Provides an immutable data wrapper to capture tokens.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class Token
{
    private final TokenType type;
    private final String text;
    private final int row;
    private final int column;

    /**
     * Creates a new Token.
     * 
     * @param type, the type of the token as defined in TokenType.
     * @param text, the text accompanying the token.
     */
    public Token(TokenType type, String text, int row, int column)
    {
        this.type = type;
        this.text = text;
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the token type.
     * 
     * @return type, the type of the token as defined in TokenType.
     */
    public TokenType getType()
    {
        return type;
    }

    /**
     * Returns the token text.
     * 
     * @return text, the token text.
     */
    public String getText()
    {
        return text;
    }

    /**
     * Returns the row number in which the token appeared in the source file input.
     * 
     * @return row, integer containing row number.
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Returns the column position in which a token appeared in the input.
     * 
     * @return column, integer containing column position.
     */
    public int getColumn()
    {
        return column;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;

        if (obj instanceof Token)
        {
            Token other = (Token) obj;

            if (other.type == this.type && other.text.equals(this.text) && other.row == this.row
                    && other.column == this.column)
            {
                result = true;
            }

        }

        return result;
    }

    @Override
    public String toString()
    {
        return String.format("{ type = %s,  text = %s, row = %d, column = %d }", type, text, row, column);
    }
}
