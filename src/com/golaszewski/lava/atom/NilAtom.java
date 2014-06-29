package com.golaszewski.lava.atom;

/**
 * Implementation of the LISP "nil" element.
 * 
 * Since all instances of nil are effectively the same, this class is
 * implemented as a singleton.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class NilAtom extends Atom {
  public static final String NIL = "nil";
  private static NilAtom instance = new NilAtom();

  /**
   * Creates a new NilAtom.
   */
  private NilAtom() {
    super(NIL);
  }

  /**
   * Returns the static instance of the nil atom.
   * 
   * @return nil.
   */
  public static NilAtom getInstance() {
    return instance;
  }
}
