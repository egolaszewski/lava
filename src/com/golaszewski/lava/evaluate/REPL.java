package com.golaszewski.lava.evaluate;

import java.io.File;

/**
 * Driver class for the lava REPL.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class REPL
{

    public static void main(String[] args)
    {        
        Interpreter i = new Interpreter();    
        i.interpret(new File("interpreter.lisp"));
        
        /*
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext())
        {
            String input = sc.nextLine();

            if (input.equals("(quit)"))
            {
                break;
            }

            try
            {
                Expression e = i.interpretString(input);
                System.out.println(e);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        sc.close();
        */
    }

}
