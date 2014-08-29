package com.golaszewski.lava.evaluate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.tokenizer.Token;
import com.golaszewski.lava.tokenizer.Tokenizer;

/**
 * Interpreter for LISP source files.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class Interpreter
{

    private Environment env;
    private Tokenizer tok;
    private ExpressionParser parser;

    public Interpreter()
    {
        env = new Environment();
        tok = new Tokenizer();
        parser = new ExpressionParser();
    }

    /**
     * Interprets a single line of input.
     * 
     * @param s, the string to interpret.
     * @return the resulting expression from the interpretation.
     */
    public Expression interpretString(String s)
    {
        Queue<Token> tokens = tok.tokenize(s);
        Expression e = parser.parse(tokens);
        return e.evaluate(env);
    }

    /**
     * Interprets the input file.
     * 
     * @param sourceFile, file containing LISP source code.
     */
    public void interpret(File sourceFile)
    {
        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader(new FileReader(sourceFile));
            String input = readInput(reader);
            Queue<Token> tokens = tok.tokenize(input);

            while (tokens.size() > 0)
            {
                Expression e = parser.parse(tokens);
                Expression r = e.evaluate(env);
                System.out.printf("%s -> %s\n", e, r);
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
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
    private String readInput(BufferedReader reader) throws IOException
    {
        StringBuilder inputBuilder = new StringBuilder();

        while (reader.ready())
        {
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
    private void closeReader(BufferedReader reader)
    {
        try
        {
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

}
