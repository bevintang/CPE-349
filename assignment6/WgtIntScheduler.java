/**

   Program: WgtIntScheduler.java
   Student: Bevin Tang
   Class:   CPE-349
   Instructor: Tim Kearns

   Description: Consider a set of jobs that each have a start time, finish time,
                and weight time. Two jobs are considered compatible if their
                start and finish times do not interfere with each other's start
                and finish times. The Weighted Interval Scheduling problem tasks
                one to find the subset of compatible jobs that yield the most
                combined weight.
   
                WgtIntScheduler proposes a Dynamically Programmed solution
                to the Weighted Interval Scheduling problem with the function:

                int[] getOptSet(int[] stime, int[] ftime, int[] weight)

                Given an array of start times, finish times, and weight times
                getOptSet() will return an array of integers (int[])
                representing the subset of compatible jobs that yield the most
                combined weight. This array will be sorted in order of
                increasing job number, and not necessarily the order in which
                the jobs are taken.

*/

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

public class WgtIntScheduler {

   /*
      Finds the nearest, previous job that has a compatible finish time to
      the current job's start time. Each index in compatible[] represents a job
      in the ascending order of finish time, and each spot in the array 
      represents the index in the sorted job list to which the index's job is
      compatible.
   */
   private static int[] calcCompatible(Job[] jobs){
      int[] compatible = new int[jobs.length];

      /* For each job, find the next compatible */
      compatible[0] = 0;
      for (int i = 1; i < jobs.length; i++){
         int stime = jobs[i].getStime();  // get current job's start time

         /* Look at finish times prev jobs until we find a compatible one */
         for (int current = i-1; current >= 0; current--){
            if (current <= 0){      // if we have reached the beginning
               compatible[i] = 0;   // no job is compatible
               break;
            }
            if (stime >= jobs[current].getFtime()){
               compatible[i] = current; // add the index of the compatible job
               break;
            }
         }
      }

      return compatible;
   }

   /*
      Finds the optimum weight per job. Each index represents a job and each
      entry represents the optimum weight that results in the sum of the
      weights in the sequence that lead up to that job. We only want to
      consider jobs that are compatible with one another, so we are interested
      in the compatible[] array.
   */
   private static int[] findOptWgt(Job[] jobs, int[] compatible){
      int[] optimal = new int[jobs.length];
      int weight = 0;
      int optChain;
      int optSoFar = 0;

      optimal[0] = 0;
      for (int i = 1; i < optimal.length; i++){
         weight = jobs[i].getWeight();       // weight of job in question
         optChain = optimal[compatible[i]];   // weight of compatible job chain
         optSoFar = optimal[i-1];            // current, most-optimal weight

         /* The optimal weight for any job sequence is either the largest one
            we have found so far, or if the current weight summed with the
            previous compatible chain. Whichever one is larger is the one we
            insert into the optimal value array.   */
         optimal[i] = Math.max(optSoFar, weight+optChain);
      }

      return optimal;
   }

   /*
      Traces back using the array of optimal weights that was found
      by findOptWgt(). traceback() will use the boolean logic of the max()
      statement used in findOptWgt() to track back through the sub problems
      that created the optimal weights in the first place.
   */
   private static int[] traceback(Job[] jobs, int[] compatible, 
                                  int[] optimalWeight){

      // Sequences will vary in length, use an ArrayList to accomodate
      ArrayList<Integer> seq = new ArrayList<Integer>();
      int current = jobs.length-1;  // tracks current position in optimal array
      int weight = 0;               // weight of job at index
      int optChain = 0;             // total weight of comptible jobs to current
      int optSoFar = 0;             // optimal local to the current position

      while (current > 0){
         weight = jobs[current].getWeight();
         optChain = optimalWeight[compatible[current]];
         optSoFar = optimalWeight[current-1];

         if (weight + optChain > optSoFar){  // add job number to sequence if
            seq.add(jobs[current].getID());  // the chain has the optimal weight
            current = compatible[current];   // set position to next compatible
         }
         else{
            current--;                       // otherwise, check next in line
         }
            
      }

      int[] optimalSequence = new int[seq.size()]; // convert sequence to int[]
      int j = 0;

      /* In the ArrayList, the steps are added in reverse order, so we must add
         these jobs to the optimalSequence array in reverse order */
      for (int i = seq.size()-1; i >= 0; i--){
         optimalSequence[i] = seq.get(j);
         j++;
      }

      return optimalSequence;
   }

   /*
      Given a list of start times, finish times, and weights of jobs, getOptSet
      finds the subset of these jobs that, if done in succession, will yield
      the most weight.

      Steps taken: (1) Sort each job by ascending order of finish times
                   (2) Build an array that will find the optimal weight per
                       job until all jobs have been considered.
                   (3) Traceback through this array and extract the job numbers
                       that were used to reach the optimal weight of all jobs
   */
   public static int[] getOptSet(int[] stime, int[] ftime, int[] weight){
      Job[] jobs = new Job[stime.length+1];

      /* Make Job object for each job */
      jobs[0] = new Job(0, 0, 0, 0);
      for (int i = 0; i < stime.length; i++)
         jobs[i+1] = new Job(stime[i], ftime[i], weight[i], i+1);

      /* (1) Sort Jobs in Ascending order of Finish Time */
      Arrays.sort(jobs);

      /* (2) Build and array that will solve sub-problem */
      int[] compatible = calcCompatible(jobs);     // find all compatible jobs
      int[] optimalWeight = findOptWgt(jobs, compatible);

      /* (3) Traceback with the optimal weight to find the optimal sequence */
      int[] optimalSequence = traceback(jobs, compatible, optimalWeight);

      /* traceback() returns the array in the order in which they are taken,
         but the spec requires that the array must be in order by job number */
      Arrays.sort(optimalSequence);
      return optimalSequence;
   }

   /*
      A private class that represents a Job. Each job includes a start time,
      finish time, weight, and a job id (this represents the job number). This
      class implements Comparable so that jobs can be sorted. This sort is used
      in the algorithm to solve the Weighted Interval Scheduling problem.
   */
   private static class Job implements Comparable<Job>{
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

      /* Implementation for use with Arrays.sort() */
      public int compareTo(Job other){
         // Ascending Order of finish time
         return ftime - other.getFtime();

         // Descending Order
         // return other.getFtime() - ftime;
       }
    }
}
