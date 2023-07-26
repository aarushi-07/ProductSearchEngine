package searchHistory;


import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map;

/*
 * Method to store entered input as history
 */

//Function to save all inputs entered by user
public class SearchHistory {
    private Map<String, Integer> searchFrequencyMap;
    private PriorityQueue<String> mostFrequentSearches;
    private int maxSize;

 // Constructor to initialize the search history with a given maximum size
    public SearchHistory(int maxSize) {
        this.maxSize = maxSize;
        searchFrequencyMap = new HashMap<>();
        //Priority queue to store the frequency of search queries
        mostFrequentSearches = new PriorityQueue<>((a, b) -> searchFrequencyMap.get(a) - searchFrequencyMap.get(b));
    }

 // Add a new search query to the history
    public void addSearch(String searchQuery) {
    	 // Increment the frequency of the search query
        int frequency = searchFrequencyMap.getOrDefault(searchQuery, 0) + 1;
        searchFrequencyMap.put(searchQuery, frequency);
     // Update the priority queue with the most frequent searches
        mostFrequentSearches.remove(searchQuery);
        mostFrequentSearches.add(searchQuery);
     // If the history exceeds the maximum size, remove the least frequent search query
        if (mostFrequentSearches.size() > maxSize) {
            searchFrequencyMap.remove(mostFrequentSearches.poll());
        }
    }

    //Print the stored history
    public void printHistory() {
        PriorityQueue<String> tempmostFrequentSearches = new PriorityQueue<>((a, b) -> searchFrequencyMap.get(b) - searchFrequencyMap.get(a));
        tempmostFrequentSearches.addAll(mostFrequentSearches);
        while (!tempmostFrequentSearches.isEmpty()) {
            String search = tempmostFrequentSearches.poll();
            System.out.println(search + " - " + searchFrequencyMap.get(search));
        }
    }
    }