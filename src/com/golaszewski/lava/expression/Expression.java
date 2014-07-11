package com.golaszewski.lava.expression;

public interface Expression {
	
  @Override
  public abstract String toString();
  public abstract String toString(boolean isHead);
  public abstract Expression evaluate(Environment env);
}
