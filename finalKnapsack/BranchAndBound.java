/*
   Description: BranchAndBound houses the branch&bound algorithm used to solve
                the 0-1 knapsack problem. This method was adapted from
                Professor Kearns' lecture on the Branch and Bound algorithm and
                its use in the 0-1 Knapsack problem.
   
     Student:  Bevin Tang
       Class:  CSC-349, Spring 2017
   Professor:  Tim Kearns

*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BranchAndBound {
   public static void branchBound (Item[] itemList, int capacity) {
      PriorityQueue<Node> pq = new PriorityQueue<Node>(new NodeKeyComparator());
      String bestPath = "";
      int maxValue = 0;
      int maxWeight = 0;
      ArrayList<Item> sortedItems = new ArrayList<Item>();
      for (int i = 0; i < itemList.length; i++) {
         sortedItems.add(itemList[i]);
      }
      Collections.sort(sortedItems, new GreedyItemComparator());
      sortedItems.add(new Item(itemList.length, 0, 0));

      Node root = new Node(0, 0, 0, capacity, sortedItems.get(0), "");
      pq.add(root);

      /* Make timer to make sure program terminates after 5 min */
      long startTime = System.currentTimeMillis();

      while (pq.size() > 0) {
         Node current = pq.poll();

         /* If current node is not a leaf node */
         if (current.key > maxValue){
            /* Check if it is safe to create a left child */
            boolean tooHeavy = false;
            Item curItem = sortedItems.get(current.level);
            int newLevel = current.level + 1;

            /* Make Left Child */
            int leftWeight = current.weight + curItem.getWeight();
            String leftPath = current.path + "1";
            Node left = new Node(newLevel, curItem.getValue() + current.value,
                                 leftWeight, capacity,
                                 sortedItems.get(newLevel), leftPath);

            /* Make right child */
            String rightPath = current.path + "0";
            Node right = new Node(newLevel, current.value, current.weight,
                                  capacity, sortedItems.get(newLevel),
                                  rightPath);

            /* Check if taking next item will exceed capacity */
            if (leftWeight > capacity){
               tooHeavy = true;
            }

            /* If not too heavy, see if left child's value is best */
            if (!tooHeavy && left.value > maxValue){
               maxValue = left.value;
               maxWeight = left.weight;
               bestPath = left.path;
            }

            /* Check if we can potentially get a better maxValue */
            // Check Left child
            if (!tooHeavy && left.key > maxValue){
               pq.add(left);
            }
            
            // Check right child
            if (right.key > maxValue){
               pq.add(right);
            }
         }

         /* If running for over 5 minutes, stop searching */
         if (System.currentTimeMillis() - startTime >= 5 * 60 * 1000){
            System.out.println("System terminated (Ran for 5 Minutes");
            break;
         }
      }

      System.out.println("Value: " + maxValue + " Weight: " + maxWeight);
      ArrayList<Item> solution = new ArrayList<Item>();
      for (int i = 0; i < bestPath.length(); i++) {
         if (bestPath.charAt(i) == '1') {
            solution.add(sortedItems.get(i));
         }
      }

      Collections.sort(solution);
      for (int j = 0; j < solution.size(); j++){
         System.out.print(solution.get(j).getIndex() + " ");
      }
      System.out.println();
   }

   /*
      Node class to represent a node in the possibility tree
   */
   private static class Node {
      public int level;
      public int value;
      public int weight;
      public double key;
      public String path;

      public Node(int level, int value, int weight, int capacity, Item curItem,
                  String path) {

         this.level = level;
         this.value = value;
         this.weight = weight;
         this.key = value + (capacity - weight) * (curItem.getRatio());
         this.path = path;
      }
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

   /*
      Comparator class to order Priority Keys in the Node Priority Queue
   */
   private static class NodeKeyComparator implements Comparator<Node> {
      public int compare(Node n1, Node n2) {
         if (n1.key < n2.key) {  // the head will have the largest key
            return 1;
         }
         else {
            return -1;
         }
      }
   }

}
