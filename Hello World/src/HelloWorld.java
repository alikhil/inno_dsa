import java.util.Random;
import java.util.Scanner;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class HelloWorld {
    public static final int N = 1000000;
    public static final int K = 10000;
    public static void main_(String[] args) {

        Scanner in = new Scanner(System.in);
        int array[] = new int[N];

        //generating random array
        Random rand = new Random();
        for(int i = 0;i < N;i++){
            array[i] = rand.nextInt(K);
            System.out.print(array[i] + " ");
        }
        long startTime = System.nanoTime();
        countingSort(array);
        long eTime = System.nanoTime() - startTime;
        for(int i = 0;i < N;i++){
            System.out.print(array[i] + " ");
        }
        System.out.println("\nExeTime = " + eTime);
        System.out.println("\nMemoryUsed = " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())));
    }

    /**
     * Sorts array
     * @param array array that need to sort
     */
    public static void countingSort(int[] array){
        //array for storing counters
        int countingArray[] = new int[K];
        //counting
        for(int i = 0;i < N;i++){
            countingArray[array[i]]++;
        }
        //rewriting
        int counter = 0;
        for(int i = 0;i < K;i++){
            while(countingArray[i] > 0) {
                array[counter++] = i;
                countingArray[i]--;
            }
        }
    }
}