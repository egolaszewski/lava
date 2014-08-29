package com.golaszewski.lava.function;

import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Implementation of the LISP 'cons' function.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class ConsFunction extends Function
{

    @Override
    public Expression call(ListExpression args, Environment env)
    {
        Expression first = getArgument(args, 0);
        Expression rest = getArgument(args, 1);
        return new ListExpression(first, rest);
    }

    @Override
    public boolean evaluateArguments()
    {
        return true;
    }

}
