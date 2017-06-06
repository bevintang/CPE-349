/**
   Description: Tests 4 algorithms that solve 0-1 Knapsack:
                  (1) Enumerate
                  (2) Greedy
                  (3) Dynamic Programming
                  (4) Branch-and-Bound
                
                Each function will output its runtime and the contents of
                the knapsack upon completion.

     Student: Bevin Tang
       Class: CSC 349, Section 1
   Professor: Tim Kearns

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
   private final int ENUM = 0;
   private final int GREEDY = 1;
   private final int DYNAMIC = 2;
   private final int BRANCH = 3;

   private static String[] scanArgs(String[] args){
      String[] data = new String[2];

      if (args.length < 2) {
         System.out.print("Usage: Driver [filename] [flag]\nAvailable ");
         System.out.println("Flags: \n   -e = Enumerate\n   -g = Greedy");
         System.out.println("   -d = Dynamic Programming");
         System.out.println("   -b = Branch and Bound");
         System.exit(1);
      }  
 
      /* Scan argv to decide which algorithm to run & to find filename */
      for (int i = 0; i < args.length; i++) {
         switch (args[i]) {
            case "-e":
               data[1] = "0";
               break;
            case "-g":
               data[1] = "1";
               break;
            case "-d":
               data[1] = "2";
               break;
            case "-b":
               data[1] = "3";
               break;
            default:
               data[0] = args[i];
               break;
         }
      }
      return data;
   }

   public static void main (String[] args) throws FileNotFoundException{
      String[] data = scanArgs(args);
      String filename = data[0];
      int state = Integer.parseInt(data[1]);

      File inputFile = new File(filename);
      Scanner fileScanner = new Scanner(inputFile);

      System.out.printf("State: %d\n", state);

      while (fileScanner.hasNext())
         System.out.println(fileScanner.nextLine());
   }
}
