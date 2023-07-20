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
	
	// Constructor for class SpellCheck
	public SpellCheck() {
		
	}

	/**
	 * Method to check input word spelling
	 * 
	 * @param inputQuery - Input to calculate suggestion
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void checkSpelling(String input) throws FileNotFoundException, IOException {

		EditDistance ed = new EditDistance();
		String line;
		ArrayList<String> dict = new ArrayList<String>();
		// object of dictionary file
		File file = new File("product.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Extracting three values from each line
                if (values.length == 3) {
                    String value1 = values[0].trim();
                    dict.add(value1);
                    }
			// to store all the words
			
			}

			int distance, edOne = 10, edTwo = 10;
			String wordOne = null;

			// iterating over each word in dictionary array list
			for (String eachWord : dict) {

				// if correct
				if (eachWord.equals(input)) {
					System.out.println("Spelling is correct");
					return;
				}

				// checking edit distance of word in dictionary to user input
				distance = ed.editDistance(eachWord, input);
				// checking if edit distance is smallest possible, in order to generate only two
				// responses
				if (distance < edTwo) {
					if (distance < edOne) {
						edOne = distance;
						wordOne = eachWord;
					}
				}
			}
			System.out.println("Please check the spelling of your input, did you mean: '" + wordOne + "' ?");
		}
	}

	/*
	 * Method provides search suggestions
	 * 
	 * @param input - Input to calculate suggestion
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void provideSuggestion(String input) throws FileNotFoundException, IOException {
		EditDistance ed = new EditDistance();
		File file = new File("product.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			ArrayList<String> dict = new ArrayList<String>();
			String line;

			while ((line = br.readLine()) != null)
				dict.add(line);
			br.close();

			int distance, firstED = 10, secondED = 10;
			String firstWord = null, secondWord = null;

			for (String word : dict) {
				distance = ed.editDistance(word, input);

				if (distance < secondED) {
					if (distance < firstED) {
						firstED = distance;
						firstWord = word;
					} else {
						secondED = distance;
						secondWord = word;
					}
				}
			}
			System.out.println("\nDid you mean: '" + firstWord + "' or '" + secondWord + "' ?");
		}
	}
}