package com.golaszewski.lava.function;

import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * Interface for built-in LISP functions.
 * 
 * @author Ennis Golaszewski
 * 
 */
public abstract class Function
{

    /**
     * Calls this function.
     * 
     * @param args, the arguments to provide to the function.
     * @param env, the execution environment.
     * @return the result of the function call.
     */
    public abstract Expression call(ListExpression args, Environment env);

    /**
     * Returns true if the arguments for the function bound to this atom need to be evaluated before being passed to the
     * function or not.
     * 
     * This is generally important for functions like 'quote', where you may want to operate on the passed argument data
     * without necessarily evaluating it right away (or at all).
     * 
     * @return true or false.
     */
    public abstract boolean evaluateArguments();

    /**
     * Retrieves the item at the specified index of the input list.
     * 
     * @param argList, the head node of the list.
     * @param index, the index to retrieve an element from.
     * @return the element at the index.
     */
    public Expression getArgument(ListExpression argList, int index)
    {
        ListExpression current = argList;
        int offset = index;

        while (offset > 0)
        {

            if (current.rest() != null)
            {
                current = (ListExpression) current.rest();
                offset -= 1;
            }
            else
            {
                throw new IllegalArgumentException(String.format("no arg exists at index %d for input %s.", index,
                        argList));
            }
        }

        return current.first();
    }

    /**
     * Counts the number of arguments in the given argument list.
     * 
     * @param argList, the head of the list.
     * @return the number of elements in the list.
     */
    public int getArgCount(ListExpression argList)
    {
        ListExpression current = argList;

        int size = 1;

        while (current.rest() instanceof ListExpression)
        {
            size++;
            current = (ListExpression) current.rest();
        }

        return size;
    }
}
