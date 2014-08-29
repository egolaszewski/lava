package com.golaszewski.lava.expression;

public interface Expression
{

    @Override
    public abstract String toString();

    /**
     * Implementation of toString that allows lists to be treated specially, in that a list expression is either the
     * head of a list or part of the tail. This means we can print lists cleanly.
     * 
     * @param isHead, is this expression the head of a list?
     * @return a string repersentation of the expression.
     */
    public abstract String toString(boolean isHead);

    /**
     * Evaluates the expression. Details of this are left up to expression implementations.
     * 
     * @param env, the environment to evaluate the expression in.
     * @return an evaluated expression.
     */
    public abstract Expression evaluate(Environment env);
}
