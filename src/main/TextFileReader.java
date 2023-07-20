package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextFileReader {
    public static void main(String[] args) {
        String filePath = "product.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Extracting three values from each line
                if (values.length == 3) {
                    String value1 = values[0].trim();
                    String value2 = values[1].trim();
                    String value3 = values[2].trim();

                    // Process the extracted values as needed
                    System.out.println("Value 1: " + value1);
                    System.out.println("Value 2: " + value2);
                    System.out.println("Value 3: " + value3);
                    System.out.println();
                } else {
                    // Handle lines with incorrect number of values
                    System.out.println("Invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
