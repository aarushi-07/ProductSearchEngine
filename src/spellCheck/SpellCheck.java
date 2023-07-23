package spellCheck;

//Import required libraries
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * @author 
 */

// Class for Spell check
public class SpellCheck {
	
	private ArrayList<String> dictionary;

    public SpellCheck() {
        dictionary = new ArrayList<>();
    }

	/**
	 * Method to check input word spelling
	 * 
	 * @param inputQuery - Input to calculate suggestion
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void checkSpelling(String input) throws IOException {
        loadDictionary("product_data.txt");

        String word = input.trim().toLowerCase();

        if (dictionary.contains(word)) {
            System.out.println("Spelling is correct");
        } else {
            System.out.println("Please check the spelling of your input, did you mean: '" + provideSuggestion(word) + "' ?");
        }
    }

	/*
	 * Method provides search suggestions
	 * 
	 * @param input - Input to calculate suggestion
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String provideSuggestion(String word) throws IOException {
        EditDistance ed = new EditDistance();
        String suggestedWord = null;
        int minDistance = Integer.MAX_VALUE;

        for (String dictWord : dictionary) {
            int distance = ed.editDistance(dictWord, word);
            if (distance < minDistance) {
                minDistance = distance;
                suggestedWord = dictWord;
            }
        }

        return suggestedWord;
    }

    public void loadDictionary(String dictionaryFilePath) throws IOException {
        File file = new File(dictionaryFilePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String value1 = line.split(",")[0].trim().toLowerCase();
                dictionary.add(value1);
            }
        }
    }
    
    public void getData(String userInput) {
        // Check if the sentence match is found
            System.out.println("User Input: " + userInput);
            try {
				checkSpelling(userInput);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
    }
}