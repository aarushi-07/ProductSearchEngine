package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import FrequencyCounter.FrequencyCounter;
import searchHistory.SearchHistory;
import searchProduct.pageRanking;
import spellCheck.SpellCheck;
import spellCheck.TST;


/**
 * Main class to start the web search engine
 * @author 
 *
 */
public class SearchEngine {

	public static final int maxCrawlLimit = 100;
	static SearchHistory history = new SearchHistory(5);

	public static void startCrawlerParser(String webPageURL) throws Exception {}

	public static void main(String[] args) throws Exception {
		System.out.println("******************************"+ 
							"\nWelcome to Product Search Engine"+
							"******************************");
		
			// Continue the code
			callingFunction();
		}

	private static void callingFunction() throws FileNotFoundException, IOException, InterruptedException {
		String input = null;
		String query = null;
		System.out.print("\n\nChoices?\n\n");
		System.out.println("1. Search a Product");
		System.out.println("2. Check if you Spelled the Product right");
		System.out.println("3. Check Product Suggestion");
		System.out.println("4. Show Frequently visited Products");
		System.out.println("5. ---------");
		System.out.println("6. Exit from search engine \n");
		System.out.println("Selection:: ");
		
		
		Scanner sc = new Scanner(System.in);
		input = sc.nextLine();

//		while (!input.toLowerCase().equals("exit")) {
			switch (input) {
			case "1":
				// Input from user
				System.out.print("Enter name of Product to search:: ");
				query = sc.nextLine();
				pageRanking search = new pageRanking();
				//Storing to history
				history.addSearch(query);
				// Call method for searching 
				try {
					search.rankPages(query.toLowerCase());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "2":
				// Input from user
				System.out.print("Enter a Product to check if it's spelled correctly:: ");
				query = sc.nextLine();
				//Storing to history
				history.addSearch(query);
				SpellCheck suggestSpelling = new SpellCheck();

				// Call method for spell-check
				suggestSpelling.checkSpelling(query.toLowerCase());
				break;

			case "3":
				// Input from user
				System.out.print("Enter a word to get it's Product suggestion:: ");
				query = sc.nextLine();
				//Storing to history
				history.addSearch(query);
				// Call method for get suggestions
				TST.suggestion(query);
				break;
			
			case "4":
				System.out.println("Frequently visited Products:: ");
				history.printHistory();
				break;

			case "5":
				//Calling function to display history
				// Prints the title of the most recent entry
				history.printHistory();
				break;
			case "6":
				System.out.println("Thank you!");
				sc.close();
				System.exit(0);
			default:
				System.out.println("Please enter a valid input");
			}

			callingFunction();
		}
}