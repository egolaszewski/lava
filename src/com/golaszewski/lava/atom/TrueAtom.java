package com.golaszewski.lava.atom;

/**
 * Implementation of the LISP '#t' atom.
 * 
 * Since all instances of '#t' are effectively the same, this class is
 * implemented as a singleton.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class TrueAtom extends Atom {
  public static final String TRUE = "#t";
  private static TrueAtom instance = new TrueAtom();
  
  /**
   * Creates a new TrueAtom.
   */
  public TrueAtom() {
    super(TRUE);
  }
  
  /**
   * Returns the static instance of the true atom.
   * 
   * @return true.
   */
  public static TrueAtom getInstance() {
    return instance;
  }

}
