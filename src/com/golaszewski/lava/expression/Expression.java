package com.golaszewski.lava.expression;

public interface Expression {
	
  @Override
  public abstract String toString();
  public abstract Expression evaluate(Environment env);
}
