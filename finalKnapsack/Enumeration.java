/*
   Description: Enumeration houses the brute force algorithm used to solve
                the 0-1 knapsack problem. This method was adapted from:
                Maya Hristakeva and Dipti Shrestha's report from the
                Computer Science Department of Simpson College, Indianola.
                http://www.micsymposium.org/mics_2005/papers/paper102.pdf.

   
     Student:  Bevin Tang
       Class:  CSC-349, Spring 2017
   Professor:  Tim Kearns

*/

import java.util.ArrayList;
import java.util.Collections;

public class Enumeration {
   public static void enumerate (Item[] itemList, int capacity){
      int numItems = itemList.length;
      int[] answer = new int[numItems];
      int[] bestChoice = new int[numItems];
      int limit = (int)Math.pow(2, numItems);
      int bestWeight = 0;
      int bestValue = 0;

      /* Go through each subset in the problem */
      for (int i = 0; i < limit; i++) {
         int j = numItems - 1;
         int tempWeight = 0;
         int tempValue = 0;

         /* Consider a new item for each iteration */
         while (answer[j] != 0 && j > 0){
            answer[j] = 0;
            --j;
         }
         answer[j] = 1; // Take this new item

         /* Go through all subsets involving these items */
         for (int k = 0; k < numItems; k++) {
            /* If we take an item, add its weight and value to temp knapsack */
            if (answer[k] == 1) {
               tempWeight = tempWeight + itemList[k].getWeight();
               tempValue = tempValue + itemList[k].getValue();
            }
         }

         /* Compare this temp Knapsack's value and weight to the old Best */
         if (tempValue > bestValue && tempWeight <= capacity) {
            bestWeight = tempWeight;   // update if necessary
            bestValue = tempValue;
            for (int l = 0; l < answer.length; l++) {
               bestChoice[l] = answer[l];
            }
         }

      }

      /* 
         Once we go through each subset and found the best, translate the best
         choice into an array of Items
      */
      ArrayList<Item> solution = new ArrayList<Item>();
      for (int i = 0; i < numItems; i++) {
         if (bestChoice[i] == 1){
            solution.add(itemList[i]);
         }
      }
      Collections.sort(solution);

      /* Print Results */
      System.out.print("Using Enumeration, the best feasible solution found: ");
      System.out.printf("Value: %d, Weight: %d\n", bestValue, bestWeight);
      for (int j = 0; j < solution.size(); j++){
         System.out.print(solution.get(j).getIndex() + " ");
      }
      System.out.println();
   }
}
