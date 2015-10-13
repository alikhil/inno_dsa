import java.io.*;

/**
 * Created by Alik Khilazhev on 27.08.2015.
 * For solving task in http://pcms.university.innopolis.ru/
 */
public class Task {
    public static void main(String[] args){
        String line = null;

        try {
			//Initialization of file reader/writer
            BufferedReader reader = new BufferedReader(new FileReader("numbers.in"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("numbers.out"));

			//input string
            String str = reader.readLine();
            //getting all words
			String[] strs = str.split(" ");
            
			for(int i = 0;i < strs.length;i++){
			//trying to parse each word
                try{
                    Long val = Long.parseLong(strs[i]);
					//if it parses write even or odd
                    writer.write((val % 2 == 0 ? "even " : "odd "));
                }
                catch(Exception e){
				//it throws exception if it doesn't parses
                    writer.write("nan ");
                }
            }
			//closing input/output streams
            reader.close();
            writer.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file");
        }
    }
}
