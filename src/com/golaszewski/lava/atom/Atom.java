package com.golaszewski.lava.atom;

/**
 * Encapsulation of a LISP atomic value.
 * 
 * @author Ennis Golaszewski
 * 
 */
public abstract class Atom {
  private String text;

  /**
   * Creates a new Atom.
   * 
   * @param text, the label of the atom.
   */
  public Atom(String text) {
    this.text = text;
  }

  /**
   * Returns the atom's text.
   * 
   * @return the atom label.
   */
  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return text;
  }
}