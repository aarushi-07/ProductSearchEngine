package scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class scrapper {
    public static void main(String[] args) {
        String url = "https://www.amazon.ca";
        int productsToExtract = 300;

        try {
            // Retry the request up to 3 times in case of a 503 error
            for (int retry = 0; retry < 3; retry++) {
                try { 
                	System.out.println("URL: " + url + "\n");
                    // Connect to the URL and retrieve the HTML document
                   Document document = Jsoup.connect(url).get();

                    // Select the product elements from the HTML
                    Elements productElements = document.select("div[data-component-type='s-search-result']");
                	/*Document document = Jsoup.connect(url)
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                            .get();*/

                    //Elements productElements = document.select(".s-result-item");

                    System.out.println("Product Elements: " + productElements + "\n");
                    
                    int count = 0;

                    // Iterate over the product elements
                    for (Element productElement : productElements) {
                        // Extract the product information
                        String title = productElement.select("span.a-text-normal").text();
                        String price = productElement.select("span.a-offscreen").text();
                        String rating = productElement.select("span.a-icon-alt").text();

                        // Print the product information
                        System.out.println("Title: " + title);
                        System.out.println("Price: " + price);
                        System.out.println("Rating: " + rating);
                        System.out.println();

                        count++;

                        // Exit the loop if the desired number of products has been extracted
                        if (count == productsToExtract) {
                            break;
                        }
                    }

                    // Break out of the retry loop if the request was successful
                    break;
                } catch (IOException e) {
                    if (retry < 2) {
                        System.out.println("Request failed. Retrying in 5 seconds...");
                        Thread.sleep(5000); // Wait for 5 seconds before retrying
                    } else {
                        throw e; // Re-throw the exception if the maximum retries have been reached
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
