package com.golaszewski.lava.evaluate;

import java.util.ArrayList;
import java.util.List;
import com.golaszewski.lava.atom.Atom;
import com.golaszewski.lava.atom.NilAtom;
import com.golaszewski.lava.atom.RawAtom;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

public class Util
{

    public static ListExpression generatePair(String left, String right)
    {
        AtomicExpression first = createAtom(left);
        AtomicExpression rest = createAtom(right);
        return new ListExpression(first, rest);
    }

    public static ListExpression generateList(String... args)
    {
        List<AtomicExpression> accumulator = new ArrayList<AtomicExpression>();

        for (String s : args)
        {
            AtomicExpression atomExpr = createAtom(s);
            accumulator.add(atomExpr);
        }

        AtomicExpression[] atoms = accumulator.toArray(new AtomicExpression[accumulator.size()]);
        ListExpression exprHead = generateExpression(atoms);

        return exprHead;
    }

    public static ListExpression generateExpression(Expression... args)
    {
        ListExpression exprHead = null;
        ListExpression current = null;
        ListExpression exprNew = null;

        for (Expression e : args)
        {
            if (exprHead == null)
            {
                exprHead = new ListExpression(e, null);
                current = exprHead;
            }
            else
            {
                exprNew = new ListExpression(e, null);
                current.setRest(exprNew);
                current = exprNew;
            }
        }

        return exprHead;
    }

    public static AtomicExpression createAtom(String text)
    {
        Atom atom = new RawAtom(text);
        return new AtomicExpression(atom);
    }

    public static AtomicExpression createNil()
    {
        return new AtomicExpression(NilAtom.getInstance());
    }

}
