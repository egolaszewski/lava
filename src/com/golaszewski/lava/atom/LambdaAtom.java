package com.golaszewski.lava.atom;

import com.golaszewski.lava.expression.AtomicExpression;
import com.golaszewski.lava.expression.Environment;
import com.golaszewski.lava.expression.Expression;
import com.golaszewski.lava.expression.ListExpression;

/**
 * 
 * @author Ennis Golaszewski
 * 
 */
public class LambdaAtom extends Atom
{
    public static final String LAMBDA = "#<Lambda>";

    private ListExpression args;
    private ListExpression body;

    /**
     * Creates a new LambdaAtom.
     */
    public LambdaAtom(ListExpression args, ListExpression body)
    {
        super(LAMBDA);
        this.args = args;
        this.body = body;
    }

    /**
     * Returns the arguments bound to this lambda.
     * 
     * @return lambda args.
     */
    public ListExpression getArgs()
    {
        return args;
    }

    /**
     * Returns the expression body of this lambda.
     * 
     * @return lambda body.
     */
    public ListExpression getBody()
    {
        return body;
    }

    /**
     * Invokes the lambda function.
     * 
     * @param inputs, the input arguments. to invoke the function with. There must be one for each bound argument.
     * @param env, the execution environment.
     * @return the result of invokation.
     */
    public Expression invoke(ListExpression inputs, Environment env)
    {
        Environment local = new Environment(env);
        pushInputs(inputs, local);
        return body.evaluate(local);
    }

    /**
     * Pushes the argument / input pairs onto the execution environment. This allows the lamba call to resolve variable
     * names in the lambda body.
     * 
     * @param inputs, the input arguments to the lambda call.
     * @param env, the execution environment.
     */
    private void pushInputs(ListExpression inputs, Environment env)
    {
        /*
         * NOTE on input vs. arg terminology. Arguments refer to the args bound to the lambda. Inputs refer to the
         * actual arguments being provided TO the lambda.
         */
        ListExpression currentInput = inputs;
        ListExpression currentArg = args;

        while (currentArg instanceof ListExpression && currentInput instanceof ListExpression)
        {

            Atom arg = getAtom(currentArg.first());
            env.bind(arg, currentInput.first());

            currentInput = (ListExpression) currentInput.rest();
            currentArg = (ListExpression) args.rest();
        }
    }

    /**
     * Retrieves an atom.
     * 
     * @param expr, an atomic expression containing an atom.
     * @return the atom.
     */
    private Atom getAtom(Expression expr)
    {
        Atom atom = ((AtomicExpression) expr).getAtom();
        return atom;
    }
}
