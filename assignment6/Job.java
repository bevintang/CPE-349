public class Job implements Comparable<Job>{
   private int stime;
   private int ftime;
   private int weight;
   private int id;

   public Job(int start, int finish, int w, int jobNum){
      stime = start;
      ftime = finish;
      weight = w;
      id = jobNum;
   }

   public int getStime(){
      return stime;
   }

   public int getFtime(){
      return ftime;
   }

   public int getWeight(){
      return weight;
   }

   public int getID(){
      return id;
   }

   public int compareTo(Job other){
      // Ascending Order of finish time
      return ftime - other.getFtime();

      // Descending Order
      // return other.getFtime() - ftime;
   }
}
