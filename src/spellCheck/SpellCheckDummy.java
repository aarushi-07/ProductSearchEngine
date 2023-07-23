package spellCheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SpellCheckDummy {

    private ArrayList<String> dictionary;

    public SpellCheckDummy() {
        dictionary = new ArrayList<>();
    }

    /**
     * Method to check input word spelling
     *
     * @param input - Input to calculate suggestion
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
     * @param word - Input to calculate suggestion
     * @return Suggested word from dictionary
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

    public boolean checkSentenceMatch(String input) throws IOException {
        String inputWithoutPunctuation = input.toLowerCase().replaceAll("[^a-z\\s]", "");
        return dictionary.contains(inputWithoutPunctuation);
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

    public static void main(String[] args) {
        SpellCheckDummy spellChecker = new SpellCheckDummy();
        spellChecker.getData("Apple iPhone 14 128GB - Yow - Unlocked");
    }
}
