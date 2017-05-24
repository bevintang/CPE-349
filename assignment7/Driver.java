/**
   
   Description: Test driver for the EditDistance class. Forms 2 strings and
                scans for a print flag to control whether or not the
                alignment text should be printed.

   Student: Bevin Tang
   Class: CPE-349
   Professor: Tim Kearns

*/

import java.util.Scanner;
import java.io.*;

public class Driver {

   public static void main (String[] args) throws FileNotFoundException{
      boolean printAlignment = false;
      String filename = "";

      /* Search for flag and filename */
      for (int i = 0; i < args.length; i++){
         String arg = args[i];
         if (arg.equals("-p"))
            printAlignment = true;
         else
            filename = arg;
      }

      /* Make new scanner for file */
      File input = new File(filename);
      Scanner fileScanner = new Scanner(input);

      /* Setup for method */
      String s1 = fileScanner.nextLine();
      String s2 = fileScanner.nextLine();
      EditDistance finder = new EditDistance(s1.length(), s2.length());

      /* Find optimal cost */
      finder.getDistance(s1, s2, printAlignment);
   }

}
