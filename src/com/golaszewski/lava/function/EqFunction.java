package com.golaszewski.lava.function;

import com.golaszewski.lava.atom.Atom;
import com.golaszewski.lava.atom.NilAtom;
import com.golaszewski.lava.atom.TrueAtom;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Implementation of the LISP 'eq' function.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class EqFunction extends Function
{

    @Override
    public Expression call(ListExpression args, Environment env)
    {
        Atom result = NilAtom.getInstance();

        Expression first = getArgument(args, 0);
        Expression second = getArgument(args, 1);

        if (first instanceof AtomicExpression && second instanceof AtomicExpression)
        {
            Atom firstAtom = ((AtomicExpression) first).getAtom();
            Atom secondAtom = ((AtomicExpression) second).getAtom();

            if (firstAtom.equals(secondAtom))
            {
                result = TrueAtom.getInstance();
            }
        }

        return new AtomicExpression(result);
    }

    @Override
    public boolean evaluateArguments()
    {
        return true;
    }

}
