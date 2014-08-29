package com.golaszewski.lava.function;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.golaszewski.lava.atom.RawAtom;
import com.golaszewski.lava.evaluate.Util;
import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Test cases for the CondFunction class.
 * 
 * @author Ennis Golaszewski
 */
public class CondFunctionTest
{
    private CondFunction condFunction = new CondFunction();
    private Environment testEnv = new Environment();

    @Test
    public void branch()
    {
        AtomicExpression nil = Util.createAtom("nil");
        ListExpression quote1 = Util.generateList("quote", "A");

        AtomicExpression t = Util.createAtom("#t");
        ListExpression quote2 = Util.generateList("quote", "B");

        ListExpression clause1 = Util.generateExpression(nil, quote1);
        ListExpression clause2 = Util.generateExpression(t, quote2);

        ListExpression args = Util.generateExpression(clause1, clause2);

        AtomicExpression result = (AtomicExpression) condFunction.call(args, testEnv);
        assertTrue(result.getAtom().equals(new RawAtom("B")));
    }
}
