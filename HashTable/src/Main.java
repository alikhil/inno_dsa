import java.io.*;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Alik Khilazhev
 * @email a.khilazhev@innopolis.ru
 * @date October 10, 2015
 * In accordance with the academic honor, I Alik Khilazhev certify that
 * the answers here are my own work without copying of others and
 * solutions from Internet or other sources."
 */

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            Map<String, String> hashMap = new ProbeHashMap<>();
            int n = Integer.parseInt(reader.readLine());
            for(int i = 0;i < n;i++){
                String[] input = reader.readLine().split(" ");
                hashMap.put(input[0],input[1]);
            }

            Set<Map.Entry<String,String>> entries = hashMap.entrySet();
            for(Map.Entry entry : entries){
                writer.write(entry.getKey() + " " + entry.getValue() + " " + entry.hashCode() + "\n");
            }

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
}



