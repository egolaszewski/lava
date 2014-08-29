package com.golaszewski.lava.function;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.golaszewski.lava.evaluate.Util;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Test suite for the ConsFunction object.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class ConsFunctionTest
{
    private ConsFunction consFunction = new ConsFunction();
    private Environment testEnv = new Environment();

    @Test
    public void cons()
    {
        ListExpression pair1 = Util.generatePair("A", "B");
        ListExpression pair2 = Util.generatePair("C", "D");
        ListExpression argsHead = new ListExpression(pair1, new ListExpression(pair2, Util.createNil()));

        ListExpression result = (ListExpression) consFunction.call(argsHead, testEnv);
        assertTrue(result.first().equals(pair1) && result.rest().equals(pair2));
    }

}
