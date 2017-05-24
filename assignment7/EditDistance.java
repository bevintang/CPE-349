/**
*/
import java.lang.StringBuilder;
import java.util.Scanner;

public class EditDistance {
   private static int GAP = 2;      // Gap penalty
   private static int MISMATCH = 1;     // Mismatch penalty
   private static int MATCH = 0;    // Match penalty
   private static int[][] table;           // Table to track optimal distance

   /* 
      Initialize table row and col size to be 1 greater than string sizes 
   */
   public EditDistance(int x, int y){
      table = new int[x+1][y+1];
      table[0][0] = 0;
   }

   /* 
      Find the optimal distance from a given pair of words 
   */
   public static void getDistance (String string1, String string2, boolean print){
      String s1 = new StringBuilder(string1).reverse().toString();
      String s2 = new StringBuilder(string2).reverse().toString();
      int distance = -1;

      /* Base Case: Gap penalty for first row and col */
      for (int i = 1; i <= s2.length(); i++) {
         table[0][i] = table[0][i-1] + GAP;
      }
      for (int i = 1; i <= s1.length(); i++) {
         table[i][0] = table[i-1][0] + GAP;
      }

      /* Fill table. For each spot:
         (1) Calculate the cost of previous neighbors:
            (A) left (costLeft)
            (B) up (costUp)
            (C) up-left (costDiag)
         (2) Assign the minimum of the neighbors to current index
      */
      int costLeft = -1;
      int costUp = -1;
      int costDiag = -1;
      int costCurrent = -1;   // cost of current letters in comparison.
                              // either a MATCH or MISMATCH

      for (int i = 1; i <= s1.length(); i++) {
         for (int j = 1; j <= s2.length(); j++) {

            /* Calculate cost of each neighbor */
            costLeft = table[i][j-1] + GAP;     // cost of insertion
            costUp = table[i-1][j] + GAP;       // cost of deletion
            costCurrent = getCost(s1.charAt(i-1), s2.charAt(j-1));
            costDiag = table[i-1][j-1] + costCurrent;

            /* Assign current optimal cost as the minimum of the neighbors */
            table[i][j] = Math.min( Math.min(costLeft, costUp), costDiag );
         }
      }

      /* Print resulting optimal distance */
      distance = table[s1.length()][s2.length()];
      System.out.println("Edit distance = " + distance);
      if (print) {
         printAlignment(s1, s2);
      }

   }

   private static int getCost(char c1, char c2) {
      if (c1 == c2)
         return MATCH;
      else
         return MISMATCH;
   }

   /* Using a filled out table, prints out the sequence-cost output */
   private static void printAlignment(String s1, String s2) {
      int i = s1.length();
      int j = s2.length();

      /* While not at first spot, check each case */
      while (i > 0 && j > 0) {
         int currentVal = table[i][j];
         int left = table[i][j-1];
         int up = table[i-1][j];
         int diag = table[i-1][j-1];

         /* Current came from left neighbor */
         if (currentVal - 2 == left) {
            System.out.println("- " + s2.charAt(j-1) + " " + GAP);
            --j;
         }

         /* Current came from upper neighbor */
         else if (currentVal - 2 == up) {
            System.out.println(s1.charAt(i-1) + " - " + GAP);
            --i;
         }

         /* Current came from diagonal neighbor */
         else if (currentVal - 1 == diag || currentVal == diag) {
            System.out.println(s1.charAt(i-1) + " " + s2.charAt(j-1) + " " +
                               getCost(s1.charAt(i-1), s2.charAt(j-1)));
            --i;               
            --j;               
         }  
            
      }
   }

}
