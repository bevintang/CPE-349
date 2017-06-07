/**

   Description: Dynamic houses the Dynamic Programming algorithm used to solve
                the 0-1 knapsack problem. This method was adapted from:
                Rajat Mishra's discussion of Dynamic Programming with reference
                to the 0-1 Knapsack posted on GeeksforGeeks:
                http://www.geeksforgeeks.org/dynamic-programming-set-10-0-1-knapsack-problem/
   
     Student:  Bevin Tang
       Class:  CSC-349, Spring 2017
   Professor:  Tim Kearns

*/

import java.util.ArrayList;
import java.util.Collections;

public class Dynamic {
   public static void dynamic(Item[] itemList, int capacity){
      int[][] valueTable = new int[itemList.length+1][capacity+1];
      int lastOptimal = -1;
      int curValue = -1;

      for (int i = 0; i <= itemList.length; i++){
         for (int j = 0; j <= capacity; j++) {
            /* Initialize First Row and Column */
            if (i == 0 || j == 0){
               valueTable[i][j] = 0;
            }
            else if (itemList[i-1].getWeight() <= j){
               int weightCurItem = itemList[i-1].getWeight();
               int valueCurItem = itemList[i-1].getValue();
               lastOptimal = valueTable[i-1][j];
               curValue = valueTable[i-1][j-weightCurItem] + valueCurItem;
               valueTable[i][j] = Math.max(curValue, lastOptimal);
            }
            else{
               valueTable[i][j] = valueTable[i-1][j];
            }
         }
      }

      /* Traceback to find which items were selected */
      int i = itemList.length;
      int j = capacity;
      int totalWeight = 0;
      int totalValue = valueTable[i][j];
      int curVal = 0;
      ArrayList<Item> solution = new ArrayList<Item>();

      /* Add an item to the solution if its value - curVal is the value of the
         last compatible optimum value */
      while (i > 0 && j > 0){
         curVal = valueTable[i][j];
         Item curItem = itemList[i-1];
         int weightDiff = j - curItem.getWeight();
         if (weightDiff < 0){
            --i;
            continue;
         }
         
         int tempVal = curVal - curItem.getValue();
         if (tempVal == valueTable[i-1][weightDiff]){
            solution.add(curItem);
            j = weightDiff;
            totalWeight += curItem.getWeight();
         }
         --i;
      }

      /* Print Results */
      Collections.sort(solution);   // organize by ascending index number
      System.out.print("Dynamic Programming solution: ");
      System.out.printf("Value: %d, Weight: %d\n", totalValue, totalWeight);
      for (int k = 0; k < solution.size(); k++){
         System.out.print(solution.get(k).getIndex() + " ");
      }
      System.out.println();
   }
}
