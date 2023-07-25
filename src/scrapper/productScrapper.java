package scrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class productScrapper {
    public void getProductData() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Aarushi\\Project\\ACC\\ProductSearchEngine\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String url = "https://shopping.google.ca/";

        // Navigate to the website
        driver.get(url);

        // List of search terms to be used
        String[] searchTerms = {
        	    "iphone",
        	    "laptop",
        	    "pendrive",
        	    "hard disk",
        	    "SSD",
        	    "smartphone",
        	    "tablet",
        	    "smartwatch",
        	    "Bluetooth headphones",
        	    "gaming mouse",
        	    "LED TV",
        	    "wireless earbuds",
        	    "fitness tracker",
        	    "external monitor",
        	    "wireless router",
        	    "Bluetooth speaker",
        	    "gaming keyboard",
        	    "action camera",
        	    "portable charger",
        	    "noise-cancelling headphones",
        	    "digital camera",
        	    "power bank",
        	    "gaming headset",
        	    "smart home devices",
        	    "e-book reader"
        	};
       
        try {
            // Create a FileWriter instance to save the data to a CSV file
            try (FileWriter writer = new FileWriter("product_data.txt")) {
                
                for (String searchTerm : searchTerms) {
                    // Find the search bar and enter the current search term
                    WebElement searchBox = driver.findElement(By.name("q"));
                    searchBox.clear();
                    searchBox.sendKeys(searchTerm);
                    searchBox.sendKeys(Keys.ENTER);

                    // Wait for the search results to load (you may need to adjust the wait time as needed)
                    Thread.sleep(5000);

                    // Process the current page
                    processPage(driver, writer);

                    // Go to the next page if available
                    WebElement nextPageLink = driver.findElement(By.xpath("//*[@id='xjs']/table/tbody/tr/td[3]/a"));
                    nextPageLink.click();
                    
                    processPage(driver, writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("----------------------------------------");
        } finally {
            // Close the browser window at the end
            driver.quit();
        }
    }

    private static void processPage(WebDriver driver, FileWriter writer) throws InterruptedException, IOException {
        // Find all the product cards on the search results page
        List<WebElement> productCards = driver.findElements(By.className("i0X6df"));

        // Check if any product cards are found
        if (productCards.isEmpty()) {
            System.out.println("No product cards found on the page.");
        } else {
            // Loop through the product cards and save the data to the CSV file
            for (WebElement productCard : productCards) {
                // Product Name
                WebElement productNameElement = productCard.findElement(By.className("tAxDx"));
                String productName = productNameElement.getText();

                // Product Price
                WebElement productPriceElement = productCard.findElement(By.cssSelector("span.a8Pemb.OFFNJ"));
                String productPrice = productPriceElement.getText();

                // Product Site
                WebElement productSiteElement = productCard.findElement(By.cssSelector("div.aULzUe.IuHnof"));
                String productSite = productSiteElement.getText();

                // Print the extracted information
				/*
				 * System.out.println("Product Name: " + productName);
				 * System.out.println("Product Price: " + productPrice);
				 * System.out.println("Product Stars: " + productStars);
				 * System.out.println("----------------------------------------");
				 */

                // Escape commas in the productName
                String escapedProductName = productName.replace("\"", "\"\"");
                escapedProductName = escapedProductName.replace(",", "");
                
                //Escape dollar in the productPrice
                String escapedproductPrice = productPrice.replace("$", "");
                
                // Save the data to the CSV file with escaped productName and productPrice
                writer.write(escapedProductName + "," + escapedproductPrice + "," + productSite + "\n");
            }
        }
    }
}
