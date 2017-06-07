/*
   Description: Greedy houses the greedy algorithm used to solve
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
import java.util.Comparator;

public class Greedy {
   public static void greedy(Item[] itemList, int capacity) {
      /* Make a copy of itemList and sort it by descending ratio */
      ArrayList<Item> sortedItems = new ArrayList<Item>();
      for (int i = 0; i < itemList.length; i++){
         sortedItems.add(itemList[i]);
      }
      Collections.sort(sortedItems, new GreedyItemComparator());
      
      /* Add item to knapsack if it fits in descending ratio order*/
      int curWeight = 0;
      int curValue = 0;
      ArrayList<Item> solution = new ArrayList<Item>();
      for (int i = 0; i < sortedItems.size(); i++){
         Item curItem = sortedItems.get(i);
         if (curItem.getWeight() + curWeight <= capacity){
            solution.add(curItem);
            curWeight += curItem.getWeight();
            curValue += curItem.getValue();
         }
      }

      /* Print Results */
      System.out.print("Greedy solution (not necessarily optimal): ");
      System.out.printf("Value: %d, Weight %d\n", curValue, curWeight);
      Collections.sort(solution);
      for (int i = 0; i < solution.size(); i++){
         System.out.print(solution.get(i).getIndex() + " ");
      }
      System.out.println();
   }

   /*
      Comparator class to order items by value-to-weight ratio (descending)
   */
   private static class GreedyItemComparator implements Comparator<Item> {
      public int compare(Item i1, Item i2) {
         if (i1.getRatio() < i2.getRatio()){
            return 1;
         }
         else {
            return -1;
         }
      }
   }
}
