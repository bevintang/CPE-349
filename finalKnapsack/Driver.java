/**

   Description: Tests 4 algorithms that solve 0-1 Knapsack:
                  (1) Enumerate
                  (2) Greedy
                  (3) Dynamic Programming
                  (4) Branch-and-Bound
                
                Each function will output its runtime and the contents of
                the knapsack upon completion.

     Student: Bevin Tang
       Class: CSC 349, Spring 2017
   Professor: Tim Kearns

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
   private static final int ENUM = 0;
   private static final int GREEDY = 1;
   private static final int DYNAMIC = 2;
   private static final int BRANCH = 3;

   /*
      Helper function: used to set the algorithm state and determine the
                       input filename.
   */
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

   /*
      Helper Function: Retrieves the number of items in the problem from the
                       input file. If the file is empty, the program will
                       terminate.
   */
   private static int getNumItems(Scanner fileScanner){
      int numItems = -1;
      if (fileScanner.hasNext()){
         numItems = fileScanner.nextInt();
         fileScanner.nextLine();
      }
      else {
         System.out.println("File is empty");
         System.exit(1);
      }
      return numItems;
   }

   /*
      Helper Function: Makes a list of Item objects from the information in
                       input file.
   */
   private static Item[] getItemList(Scanner fileScanner, int numItems) {
      Item[] itemList = new Item[numItems];
      for (int i = 0; i < numItems; i++) {
         // Trim Delimit by all whitespace
         String[] lineData = fileScanner.nextLine().trim().split("\\s+");
         int index = Integer.parseInt(lineData[0]);
         int value = Integer.parseInt(lineData[1]);
         int weight = Integer.parseInt(lineData[2]);
         Item item = new Item(index, value, weight);
         itemList[i] = item;
      }
      return itemList;
   }

   /*
      Main Function: Organizes data from input file to be used in knapsack
                     algorithm implementations.
   */
   public static void main (String[] args) throws FileNotFoundException{
      String[] data = scanArgs(args);              // filter arguments
      String filename = data[0];
      int state = Integer.parseInt(data[1]);
      File inputFile = new File(filename);
      Scanner fileScanner = new Scanner(inputFile);
      int numItems = getNumItems(fileScanner);
      Item[] items = getItemList(fileScanner, numItems);
      int capacity = fileScanner.nextInt();

      /* Run an algorithm determined by the state value */
      switch (state) {
         case ENUM:
            Enumeration.enumerate(items, capacity);
            break;
         case GREEDY:
            Greedy.greedy(items, capacity);
            break;
         case DYNAMIC:
            Dynamic.dynamic(items, capacity);
            break;
         case BRANCH:
            BranchAndBound.branchBound(items, capacity);
            break;
      }
      
   }
}

