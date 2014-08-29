package com.golaszewski.lava.function;

import com.golaszewski.lava.atom.LambdaAtom;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Implementation of the LISP 'lamba' function.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class LambdaFunction extends Function
{
    @Override
    public Expression call(ListExpression args, Environment env)
    {
        // TODO ensure both arguments and body come in list form. report a good error if not.
        ListExpression lambdaArgs = (ListExpression) getArgument(args, 0);
        ListExpression expr = (ListExpression) getArgument(args, 1);
        LambdaAtom lambda = new LambdaAtom(lambdaArgs, expr);
        return new AtomicExpression(lambda);
    }

    @Override
    public boolean evaluateArguments()
    {
        return false;
    }
}
