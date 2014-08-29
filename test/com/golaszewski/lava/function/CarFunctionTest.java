package com.golaszewski.lava.function;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.golaszewski.lava.evaluate.Util;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Test suite for the CarFunction class.
 * 
 * @author Ennis Golaszewski
 */
public class CarFunctionTest
{

    private CarFunction carFunction = new CarFunction();
    private Environment testEnv = new Environment();

    @Test(expected = IllegalArgumentException.class)
    public void carAtom()
    {
        ListExpression args = Util.generateList("A");
        carFunction.call(args, testEnv);
    }

    @Test
    public void carPair()
    {
        ListExpression args = Util.generatePair("A", "B");
        ListExpression argsHead = new ListExpression(args, Util.createNil());

        Expression result = carFunction.call(argsHead, testEnv);
        assertTrue(result.equals(args.first()));
    }

    @Test
    public void carList()
    {
        ListExpression args = Util.generateList("A", "B", "C", "D", "E");
        ListExpression argsHead = new ListExpression(args, Util.createNil());

        Expression result = carFunction.call(argsHead, testEnv);
        assertTrue(result.equals(args.first()));
    }
}
