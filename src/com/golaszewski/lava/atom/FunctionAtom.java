package com.golaszewski.lava.atom;

import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;
import com.golaszewski.lava.function.Function;

/**
 * Wrapper for an atom that is evaluated to a function call. Function call here is defined as an inbuilt LISP function,
 * not a user defined lambda.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class FunctionAtom extends Atom
{
    public static final String FUNCTION = "#<Function>";
    private Function funct;

    /**
     * Creates a new FunctionAtom.
     * 
     * @param functionName, the name of the function.
     */
    public FunctionAtom(Function funct)
    {
        super(FUNCTION);
        this.funct = funct;
    }

    /**
     * Calls the function bound to this function atom.
     * 
     * @param args, the arguments to provide the function being called.
     * @param env, the execution environment.
     * @return the result of the function call.
     */
    public Expression call(ListExpression args, Environment env)
    {
        return funct.call(args, env);
    }

    /**
     * Returns true if the arguments for the function bound to this atom need to be evaluated or not.
     * 
     * @return true or false.
     */
    public boolean evaluateArguments()
    {
        return funct.evaluateArguments();
    }

    @Override
    public String toString()
    {
        return String.format("#<Function.%s>", funct);
    }
}
