package searchProduct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Class for Pattern search
 * 
 * @author
 */

//class that searches patterns that match the string searchPattern
public class SearchPattern {

    /**
     * Searches patterns that match the string searchPattern
     *
     * @param searchPattern
     * @return Hashtable<String, Integer>
     * @throws IOException
     */
    public Hashtable<String, Integer> searchPatterns(String searchPattern) throws IOException {
        // changing it to lower case in order to avoid case-sensitivity.
        searchPattern = searchPattern.toLowerCase();
        // Hashtable that stores the rank of words found while going through the list.
        Hashtable<String, Integer> pageRank = new Hashtable<String, Integer>();
        // Path to the CSV file to be searched
        String filePath = "product.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Splitting the line using comma as the delimiter
                String[] values = line.split(",");

                // Extracting the first word (product name) from each line
                if (values.length >= 1) {
                    String productName = values[0].trim().toLowerCase();

                    // Checking if the product name matches the search pattern
                    if (productName.contains(searchPattern)) {
                        // Using hashtable merge function to count the occurrences of the product name
                        pageRank.merge(productName, 1, Integer::sum);
                    }
                }
            }
        }

        // Returning the Hashtable pageRank containing the occurrence of the word within the file.
        return pageRank;
    }
}
