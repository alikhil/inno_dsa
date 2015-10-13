import java.io.*;

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

            HashMap<String, String> hashMap = new HashMap<>();
            int n = Integer.parseInt(reader.readLine());
            for(int i = 0;i < n;i++){
                String[] input = reader.readLine().split(" ");
                hashMap.put(input[0],input[1]);
            }

            HashMap.Entry[] entries = hashMap.entrySet();
            for(HashMap.Entry entry : entries){
                writer.write(entry.key + " " + entry.value + " " + entry.hash + "\n");
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



