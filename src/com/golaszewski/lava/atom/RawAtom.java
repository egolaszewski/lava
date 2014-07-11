package com.golaszewski.lava.atom;

/**
 * Implementation of a raw atom. This is very similar to the base atom class,
 * but it specifically overrides equals() and hashCode() in order to accomodate
 * for the LISP 'eq' function.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class RawAtom extends Atom {

  /**
   * Creates a new RawAtom.
   * 
   * @param text, the text contained in the atom.
   */
  public RawAtom(String text) {
    super(text);
  }

  @Override
  public int hashCode() {
    String text = super.getText();

    final int prime = 31;
    int result = 1;
    result = prime * result + ((text == null) ? 0 : text.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    String text = super.getText();

    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Atom other = (RawAtom) obj;

    if (text == null) {
      if (other.getText() != null) {
        return false;
      }
    } else if (!text.equals(other.getText())) {
      return false;
    }

    return true;
  }
}
