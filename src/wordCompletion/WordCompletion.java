package wordCompletion;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

/*
 * Class to implement word completion functionality using Trie data structure
 * @author Aarushi Bagri
 */

class TrieNode {
	// Map to store child nodes
	final Map<Character, TrieNode> children;

	// Flag to mark the end of a word
	private boolean isEndOfWord;

	public TrieNode() {
		children = new HashMap<>();
		isEndOfWord = false;
	}

	// Retrieve the child node for a character
	public TrieNode getChild(char ch) {
		return children.get(Character.toLowerCase(ch));
	}

	// Set the child node for a character
	public void setChild(char ch, TrieNode node) {
		children.put(Character.toLowerCase(ch), node);
	}

	// Check if the node marks the end of a word
	public boolean isEndOfWord() {
		return isEndOfWord;
	}

	// Set the flag to mark the end of a word
	public void setEndOfWord(boolean endOfWord) {
		isEndOfWord = endOfWord;
	}
}

/*
 * Main class to provide word completion functionality
 */
public class WordCompletion {
	private TrieNode root;

	public WordCompletion() {
		root = new TrieNode();
	}

	/*
     * Method to insert a word into the Trie
     * @param word: The word to be inserted
     */
	public void insert(String word) {
		TrieNode node = root;
		for (char ch : word.toCharArray()) {
			// If the character is not present in the children, add a new node
			if (node.getChild(ch) == null) {
				node.setChild(ch, new TrieNode());
			}
			node = node.getChild(ch);
		}
		node.setEndOfWord(true);
	}
	
	/*
     * Method to search for a word in the Trie
     * @param word: The word to be searched
     * @return true if the word is found, false otherwise
     */
	public boolean search(String word) {
		TrieNode node = root;
		for (char ch : word.toCharArray()) {
			node = node.getChild(ch);
			if (node == null) {
				return false;
			}
		}
		return node.isEndOfWord();
	}
	
	/*
     * Method to check if any word starts with a given prefix
     * @param prefix: The prefix to be checked
     * @return true if there is a word starting with the prefix, false otherwise
     */
	public boolean startsWith(String prefix) {
		TrieNode node = root;
		for (char ch : prefix.toCharArray()) {
			// Traverse through the Trie to find the prefix
			node = node.getChild(ch);
			if (node == null) {
				// If any character is not present, there is no word with the given prefix
				return false;
			}
		}
		return true;
	}

	/*
     * Method to get word suggestions based on a given prefix
     * @param prefix: The prefix for which word suggestions are required
     * @return A list of word suggestions that start with the given prefix
     */
	public List<String> getSuggestions(String prefix) {
		String productFilePath = "product_data.txt";
		List<String> fileList = readProductFromFile(productFilePath);
		for (String trieValue : fileList)
			insert(trieValue);

		List<String> suggestions = new ArrayList<>();
		TrieNode node = root;
		for (char ch : prefix.toCharArray()) {
			node = node.getChild(ch);
			if (node == null) {

				return suggestions;
			}
		}
		findSuggestions(node, prefix, suggestions);
		return suggestions;
	}

	/*
     * Method to read words from a file and store them in a list
     * @param productFilePath: The path of the file containing the words
     * @return A list of words read from the file
     */
	private List<String> readProductFromFile(String productFilePath) {
		List<String> products = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(productFilePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				String product = data[0].trim();
				products.add(product);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return products;
	}

	/*
     * Method to find word suggestions for a given prefix using DFS traversal of the Trie
     * @param node: The current node being explored in the Trie
     * @param currentWord: The word formed till the current node during the traversal
     * @param suggestions: The list to store word suggestions
     */
	private void findSuggestions(TrieNode node, String currentWord, List<String> suggestions) {
		if (node.isEndOfWord()) {
			suggestions.add(currentWord);
		}

		for (char ch : node.children.keySet()) {
			TrieNode childNode = node.getChild(ch);
			if (childNode != null) {
				findSuggestions(childNode, currentWord + ch, suggestions);
			}
		}
	}
}