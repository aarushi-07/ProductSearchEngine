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
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Aarushi\\Project\\ACC\\ProductSearchEngine\\drivers\\chromedriver.exe");
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
//        	    "smartphone",
//        	    "tablet",
//        	    "smartwatch",
//        	    "Bluetooth headphones",
//        	    "gaming mouse",
//        	    "LED TV",
//        	    "wireless earbuds",
//        	    "fitness tracker",
//        	    "external monitor",
//        	    "wireless router",
//        	    "Bluetooth speaker",
//        	    "gaming keyboard",
//        	    "action camera",
//        	    "portable charger",
//        	    "noise-cancelling headphones",
//        	    "digital camera",
//        	    "power bank",
//        	    "gaming headset",
//        	    "smart home devices",
//        	    "e-book reader"
        	};
       
        try {
            try (FileWriter writer = new FileWriter("product_data.txt")) {
                for (String searchTerm : searchTerms) {
                    WebElement searchBox = driver.findElement(By.name("q"));
                    searchBox.clear();
                    searchBox.sendKeys(searchTerm);
                    searchBox.sendKeys(Keys.ENTER);

                    Thread.sleep(5000);

                    processPage(driver, writer);

                    WebElement nextPageLink = driver.findElement(By.xpath("//*[@id='xjs']/table/tbody/tr/td[3]/a"));
                    nextPageLink.click();
                    
                    processPage(driver, writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    
    }

    private static void processPage(WebDriver driver, FileWriter writer) throws InterruptedException, IOException {
        List<WebElement> productCards = driver.findElements(By.className("i0X6df"));

        if (productCards.isEmpty()) {
            System.out.println("No product cards found on the page.");
        } else {
            for (WebElement productCard : productCards) {
                WebElement productNameElement = productCard.findElement(By.className("tAxDx"));
                String productName = productNameElement.getText();

                WebElement productPriceElement = productCard.findElement(By.cssSelector("span.a8Pemb.OFFNJ"));
                String productPrice = productPriceElement.getText();

                WebElement productSiteElement = productCard.findElement(By.cssSelector("div.aULzUe.IuHnof"));
                String productSite = productSiteElement.getText();

                if (productName.isEmpty() || productPrice.isEmpty() || productSite.isEmpty()) {
                    // Skip writing to the CSV file if any of the data fields is empty
                    continue;
                }

                String escapedProductName = productName.replace("\"", "\"\"");
                escapedProductName = escapedProductName.replace(",", "");

                String escapedProductPrice = productPrice.replace("$", "");
                escapedProductPrice = escapedProductPrice.replace(",", "");

                writer.write(escapedProductName + "," + escapedProductPrice + "," + productSite + "\n");
            }
        }
    }
}
