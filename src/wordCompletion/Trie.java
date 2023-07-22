package wordCompletion;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class TrieNodes {
    final Map<Character, TrieNodes> children;
    private boolean isEndOfWord;

    public TrieNodes() {
        children = new HashMap<>();
        isEndOfWord = false;
    }

    public TrieNodes getChild(char ch) {
        return children.get(ch);
    }

    public void setChild(char ch, TrieNodes node) {
        children.put(ch, node);
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
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
