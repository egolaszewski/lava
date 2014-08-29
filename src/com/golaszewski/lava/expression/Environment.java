package com.golaszewski.lava.expression;

import java.util.HashMap;
import java.util.Map;
import com.golaszewski.lava.atom.Atom;
import com.golaszewski.lava.atom.FunctionAtom;
import com.golaszewski.lava.atom.NilAtom;
import com.golaszewski.lava.atom.TrueAtom;
import com.golaszewski.lava.function.AtomFunction;
import com.golaszewski.lava.function.CarFunction;
import com.golaszewski.lava.function.CdrFunction;
import com.golaszewski.lava.function.CondFunction;
import com.golaszewski.lava.function.ConsFunction;
import com.golaszewski.lava.function.EqFunction;
import com.golaszewski.lava.function.Function;
import com.golaszewski.lava.function.LabelFunction;
import com.golaszewski.lava.function.LambdaFunction;
import com.golaszewski.lava.function.QuoteFunction;

/**
 * Encapsulates an execution environment.
 * 
 * @author egolaszewski
 * 
 */
public class Environment
{
    private static Map<String, Expression> globals;

    private static AtomicExpression createFunctionAtom(Function funct)
    {
        FunctionAtom functAtom = new FunctionAtom(funct);
        return new AtomicExpression(functAtom);
    }

    static
    {
        globals = new HashMap<String, Expression>();

        globals.put("quote", createFunctionAtom(new QuoteFunction()));
        globals.put("car", createFunctionAtom(new CarFunction()));
        globals.put("cdr", createFunctionAtom(new CdrFunction()));
        globals.put("cons", createFunctionAtom(new ConsFunction()));
        globals.put("lambda", createFunctionAtom(new LambdaFunction()));
        globals.put("label", createFunctionAtom(new LabelFunction()));
        globals.put("eq", createFunctionAtom(new EqFunction()));
        globals.put("cond", createFunctionAtom(new CondFunction()));
        globals.put("atom", createFunctionAtom(new AtomFunction()));

        globals.put("nil", new AtomicExpression(NilAtom.getInstance()));
        globals.put("#t", new AtomicExpression(TrueAtom.getInstance()));
    }

    private Map<String, Expression> locals;

    private Expression findBinding(String key)
    {
        Expression binding = locals.get(key);

        if (null == binding)
        {
            binding = globals.get(key);
        }

        if (null == binding)
        {
            handleUnbound(key);
        }

        return binding;
    }

    private void handleUnbound(String key)
    {
        String error = String.format("symbol %s is unbound.", key);
        System.out.printf("dump of the environment: %s\n", locals);
        throw new IllegalArgumentException(error);
    }

    public Environment()
    {
        locals = new HashMap<String, Expression>();
    }

    /**
     * Retrieves the binding pair for the input atom. Local bindings will be searched first, followed by global
     * bindings. If a match is not made in either, an exception is thrown.
     * 
     * @param binding, the binding to search for.
     * @return the bound expression or element.
     */
    public Expression getBinding(Atom binding)
    {
        String text = binding.getText();
        return findBinding(text);
    }

    /**
     * Binds the input value to the atom's name.
     * 
     * @param binding, the atom holding the text for the binding.
     * @param value, the value to bind to the name.
     */
    public void bind(Atom binding, Expression value)
    {
        locals.put(binding.getText(), value);
    }

    /**
     * Unbinds the expression bound to the input atom's name.
     * 
     * @param binding, the atom.
     */
    public void unbind(Atom binding)
    {
        locals.remove(binding.getText());
    }

    @Override
    public String toString()
    {
        return String.format("globals: %s, locals: %s\n", globals, locals);
    }
}
