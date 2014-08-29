package com.golaszewski.lava.function;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.golaszewski.lava.evaluate.Util;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Test suite for the CdrFunction object.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class CdrFunctionTest
{
    private CdrFunction cdrFunction = new CdrFunction();
    private Environment testEnv = new Environment();

    @Test(expected = IllegalArgumentException.class)
    public void cdrAtom()
    {
        ListExpression args = Util.generateList("A");
        cdrFunction.call(args, testEnv);
    }

    @Test
    public void cdrPair()
    {
        ListExpression args = Util.generatePair("A", "B");
        ListExpression argsHead = new ListExpression(args, Util.createNil());

        Expression result = cdrFunction.call(argsHead, testEnv);
        assertTrue(result.equals(args.rest()));
    }

    @Test
    public void cdrList()
    {
        ListExpression args = Util.generateList("A", "B", "C", "D", "E");
        ListExpression argsHead = new ListExpression(args, Util.createNil());

        Expression result = cdrFunction.call(argsHead, testEnv);
        assertTrue(result.equals(args.rest()));
    }
}
