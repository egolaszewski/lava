package com.golaszewski.lava.evaluate;

import java.util.Scanner;

import com.golaszewski.lava.expression.Expression;

/**
 * Driver class for the lava REPL.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class REPL {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Interpreter i = new Interpreter();

    while (sc.hasNext()) {
      String input = sc.nextLine();

      if (input.equals("(quit)")) {
        break;
      }

      try {
        Expression e = i.interpretString(input);
        System.out.println(e);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    sc.close();
  }

}
