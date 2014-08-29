package com.golaszewski.lava.function;

import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Implementation of the LISP 'quote' function.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class QuoteFunction extends Function
{

    @Override
    public Expression call(ListExpression args, Environment env)
    {
        return args.first();
    }

    @Override
    public boolean evaluateArguments()
    {
        return false;
    }

}
