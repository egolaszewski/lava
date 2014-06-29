package com.golaszewski.lava.expression;

import com.golaszewski.lava.atom.Atom;
import com.golaszewski.lava.atom.NilAtom;
import com.golaszewski.lava.atom.TrueAtom;

/**
 * Expression consisting of a single atom.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class AtomicExpression implements Expression {
  private static AtomicExpression trueInstance;
  private static AtomicExpression nilInstance;

  static {
    trueInstance = new AtomicExpression(TrueAtom.getInstance());
    nilInstance = new AtomicExpression(NilAtom.getInstance());
  }

  private Atom atom;

  /**
   * Returns a singleton instance of an atomic expression wrapping the 'true'
   * atom.
   * 
   * @return true atomic expression.
   */
  public static AtomicExpression getTrueAtom() {
    return trueInstance;
  }

  /**
   * Returns a singleton instance of an atomic expression wrapping the 'nil'
   * atom.
   * 
   * @return nil atomic expression.
   */
  public static AtomicExpression getNilAtom() {
    return nilInstance;
  }

  /**
   * Creates a new AtomicExpression.
   * 
   * @param atom, the atom contained in the expression.
   */
  public AtomicExpression(Atom atom) {
    this.atom = atom;
  }

  @Override
  public String toString() {
    return atom.toString();
  }

  @Override
  public Expression evaluate(Environment env) {
    Expression binding = env.getBinding(atom);
    return binding;
  }

  public Atom getAtom() {
    return atom;
  }
}
