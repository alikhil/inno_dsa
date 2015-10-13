import java.io.*;

/**
 * @author Alik Khilazhev
 * @email a.khilazhev@innopolis.ru
 * @date September 29, 2015
 * In accordance with the academic honor, I Alik Khilazhev certify that
 * the answers here are my own work without copying of others and
 * solutions from Internet or other sources."
 */

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("sort.in"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("sort.out"));
            Comparable[] array = new Comparable[1];
            String[] inputs = reader.readLine().split(" ");
            String value = reader.readLine();
            Comparable comparableValue = value;
            int n = inputs.length - 1;
            if (inputs[0].equals("String")) {
                array = new String[n];
                for (int i = 1; i < inputs.length; i++)
                    array[i - 1] = inputs[i];
            }

            if (inputs[0].equals("Float")) {
                array = new Float[n];
                for (int i = 1; i < inputs.length; i++)
                    array[i - 1] = Float.parseFloat(inputs[i]);
                comparableValue = Float.parseFloat(value);
            }

            if (inputs[0].equals("Integer")) {
                array = new Integer[n];
                for (int i = 1; i < inputs.length; i++)
                    array[i - 1] = Integer.parseInt(inputs[i]);
                comparableValue = Integer.parseInt(value);
            }
            MegaLibrary.sort(array, n);
            Integer answer = MegaLibrary.binarySearch(array, comparableValue, 0, n - 1);
            writer.write(answer.toString());
            reader.close();
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file");
        }
    }

    static class MegaLibrary {

        /***
         * Sorting array
         * @param array array that need to sort
         * @param n length of array
         */
        public static void sort(Comparable[] array, int n) {
            int j;
            Comparable cur;
            for (int i = 1; i < n; i++) {
                j = i;
                cur = array[i];
                while (j > 0 && array[j - 1].compareTo(cur) > 0) {
                    array[j] = array[j - 1];
                    j--;
                }
                array[j] = cur;
            }
        }

        /***
         * Searches value in sorted array and returns it's index
         * @param array sorted array where need to find
         * @param value value which need to find
         * @param minIndex minimal index that can be return
         * @param maxIndex maximal index that can be return
         * @return
         */
        public static int binarySearch(Comparable[] array, Comparable value, int minIndex, int maxIndex) {
            int mid;
            while (maxIndex - minIndex > 1) {
                mid = (maxIndex + minIndex) / 2;
                if (array[mid].compareTo(value) <= 0)
                    minIndex = mid;
                else
                    maxIndex = mid;
            }
            if (array[minIndex].equals(value))
                return minIndex;
            return -1;
        }

    }
}



