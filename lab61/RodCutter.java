import java.util.*;
import java.io.*;


/*
   Calculates the total price per cut for each length of the rod.
*/
public class RodCutter{
   private static int[] cutRod(int[] prices){
      int length = prices.length + 1;
      int[] maxValues = new int[length];  // tracks max prices for each length
      int[] cutsMade = new int [length];  // tracks the cuts made for each length
      int maxSoFar = 0;
      int tempMax = 0;

      maxValues[0] = 0;

      /* For each length, check all combinations of cuts and find the largest */
      for (int i = 1; i < length; i++){   
         maxSoFar = -1;       // reset max

         /* Hypothetically cut all portions until current length.
            Find the largest price for any of these combinations */
         for (int j = 0; j < i; j++){
            tempMax = prices[j] + maxValues[i-j-1];
            if (maxSoFar < tempMax){   // if we finda value larger thant he max,
               maxSoFar = tempMax;     // set it as the new max
               cutsMade[i] = j+1;      // store the cut length you made
            }
         }

         maxValues[i] = maxSoFar;      // store the max value for this length
         System.out.println("total for length " + i + "\t= " + maxSoFar);
      }

      System.out.println();
      return cutsMade;
   }

   /*
      Prints the results of the optimal case
   */
   private static void printCases(int[] prices){
      int[] cutsMade = cutRod(prices); //fetch number of cuts per length of rod
      int[] optimalCuts = new int[prices.length + 1]; // stores the cuts of most optimal
      int rodLength = prices.length;   // length will decrease after every cut
      int lengthCut = 0;               // how much will be cut off after a cut

      /* While there is some rod left, choose the most optimal cut per case
         and increment the number of optimalCuts for that length */
      while (rodLength > 0){
         lengthCut = cutsMade[rodLength];    // find length of cut
         optimalCuts[lengthCut]++;           // increment # cuts @ length
         rodLength -= cutsMade[rodLength];   // update rod length
      }

      /* Go through the optimalCuts and print any existing cuts */
      for (int i = 0; i < optimalCuts.length; i++){
         int numCuts = optimalCuts[i];

         if (0 != numCuts)
            System.out.println("Number of rods of length " + i + "\t= " + numCuts);
      }
   }

   public static void main (String[] args) throws FileNotFoundException{
      File input = new File(args[0]);

      if (!input.exists()){
         System.out.println("File not found");
         System.exit(0);
      }  

      Scanner fileScanner = new Scanner(input);
      int caseNumber = Integer.parseInt(fileScanner.nextLine().split("\\s+")[0]);

      /* For each case, extract the number of lengths and prices per length */
      for (int i = 1; i <= caseNumber; i++){
         System.out.println("\n\nCase " + i);
         int length = Integer.parseInt(fileScanner.nextLine().split("\\s+")[0]);
         String[] priceValues = fileScanner.nextLine().split("\\s+");
         int prices[] = new int[length];

         /* Transfer string data into integer data */
         for (int j = 0; j < length; j++)
            prices[j] = Integer.parseInt(priceValues[j]);

         /* Find and Print the Optimal Cases */
         printCases(prices);
      }
   }
}
