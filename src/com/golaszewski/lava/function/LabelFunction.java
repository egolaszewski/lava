package com.golaszewski.lava.function;

import com.golaszewski.lava.atom.TrueAtom;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Implementation of a LISP 'label' function.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class LabelFunction extends Function
{

    @Override
    public Expression call(ListExpression args, Environment env)
    {
        AtomicExpression label = (AtomicExpression) getArgument(args, 0);
        Expression value = getArgument(args, 1);
        env.bind(label.getAtom(), value.evaluate(env));
        return new AtomicExpression(TrueAtom.getInstance());
    }

    @Override
    public boolean evaluateArguments()
    {
        return false;
    }

}
