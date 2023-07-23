package wordCompletion;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

/*
 * Class to find Product suggestion based on prefix
 * @author Aarushi Bagri
 */

class TrieNode {
    final Map<Character, TrieNode> children;
    private boolean isEndOfWord;

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }

    public TrieNode getChild(char ch) {
        return children.get(Character.toLowerCase(ch));
    }

    public void setChild(char ch, TrieNode node) {
        children.put(Character.toLowerCase(ch), node);
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
}

public class WordCompletion {
    private TrieNode root;

    public WordCompletion() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (node.getChild(ch) == null) {
                node.setChild(ch, new TrieNode());
            }
            node = node.getChild(ch);
        }
        node.setEndOfWord(true);
    }

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

    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            node = node.getChild(ch);
            if (node == null) {
                return false;
            }
        }
        return true;
    }

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