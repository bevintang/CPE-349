import java.util.Scanner;
import java.io.*;

public class Driver {

   public static void main (String[] args) throws FileNotFoundException{
      boolean printAlignment = false;
      String filename = "";

      for (int i = 0; i < args.length; i++){
         String arg = args[i];
         if (arg.equals("-p"))
            printAlignment = true;
         else
            filename = arg;
      }

      File input = new File(filename);
      Scanner fileScanner = new Scanner(input);

      String s1 = fileScanner.nextLine();
      String s2 = fileScanner.nextLine();
      EditDistance finder = new EditDistance(s1.length(), s2.length());

      finder.getDistance(s1, s2, printAlignment);
   }

}
