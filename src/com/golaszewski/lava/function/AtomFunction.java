package com.golaszewski.lava.function;

import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Implementation of the 'atom' function from LISP. This function checks if the input is an atom, then returns a true or
 * nil atom accordingly.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class AtomFunction extends Function
{

    @Override
    public Expression call(ListExpression args, Environment env)
    {
        Expression arg = getArgument(args, 0);

        if (arg instanceof AtomicExpression)
        {
            return AtomicExpression.getTrueAtom();
        }
        else
        {
            return AtomicExpression.getNilAtom();
        }
    }

    @Override
    public boolean evaluateArguments()
    {
        return true;
    }

}
