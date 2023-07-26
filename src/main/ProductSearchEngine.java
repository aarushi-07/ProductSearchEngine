package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import FrequencyCounter.FrequencyCounter;
import searchHistory.SearchHistory;
import searchProduct.pageRanking;
import sortProduct.sortProduct;
import spellCheck.SpellCheck;
import spellCheck.TST;
import wordCompletion.WordCompletion;
import scrapper.productScrapper;


/**
 * Main class to start the web search engine
 * @author Aarushi Bagri, Darsh Bhavesh Bhatt, Dhvani Sheth
 * Riddhi Yogesh Jobanputra, Aesha Indravadan Mehta
 *
 */
public class ProductSearchEngine {

	static SearchHistory history = new SearchHistory(5);

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
		System.out.println("3. Sort product based on criteria");
		System.out.println("4. Show Frequently visited Products");
		System.out.println("5. Product suggestion based on product prefix");
		System.out.println("6. Get Product data from Google");
		System.out.println("7. Exit from search engine \n");
		System.out.println("Selection:: ");
		
		
		Scanner sc = new Scanner(System.in);
		input = sc.nextLine();

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
				suggestSpelling.getData(query);
				break;

			case "3":
				System.out.println("Enter the sorting criteria: (1) Name  (2) Price  (3) Site");
				int choice = sc.nextInt();
				// Call method for sorting
				sortProduct sp = new sortProduct();
				sp.sortCriteria(choice);
				break;
			
			case "4":
				System.out.println("Frequently visited Products:: ");
				history.printHistory();
				break;

			case "5":
				System.out.print("Enter a Product prefix to complete it's suggestion:: ");
				query = sc.nextLine();
				
				  WordCompletion wc = new WordCompletion(); 
				  //Getting list of suggestions
				  List<String> suggestions = wc.getSuggestions(query); 
				  if (!suggestions.isEmpty()) {
				  System.out.println("Suggestions for prefix '" + query + "':: ");
				  for (String word : suggestions) 
				  { System.out.println(word); 
				  	//Storing to history
					history.addSearch(word);
					}
				  } 
				  else {
				  System.out.println("No suggestions found for prefix '" + query + "'."); 
				  }
				
				break;
			case "6":
				System.out.println("Geting Product data from Google and saving it into product_data.txt");
				productScrapper ps = new productScrapper();
				ps.getProductData();
			case "7":
				System.out.println("Thank you!");
				sc.close();
				System.exit(0);
			default:
				System.out.println("Please enter a valid input");
			}

			callingFunction();
		}
}