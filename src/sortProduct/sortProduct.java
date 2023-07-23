package sortProduct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Product {
    String name;
    double price;
    double rating;

    Product(String name, double price, double rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
    }
}

public class sortProduct {
	
	public void sortCriteria(String criteria) {
    String filePath = "product_data.txt";

    List<Product> productList = readDataFromFile(filePath);
    if (productList != null) {
        // Sort based on criteria
        if (criteria.equalsIgnoreCase("price")) {
            mergeSort(productList, Comparator.comparingDouble(product -> product.price));
		} /*
			 * else if (criteria.equalsIgnoreCase("rating")) { mergeSort(productList,
			 * Comparator.comparingDouble(product -> product.rating));
			 
        } */else {
            System.out.println("Invalid sort criteria: " + criteria);
            return;
        }

        // Print the sorted list
        for (Product product : productList) {
            System.out.println(product.name + ", " + product.price + ", " + product.rating);
        }
    }
}

    private static List<Product> readDataFromFile(String filePath) {
        List<Product> productList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String product = data[0].trim();
                double price = Double.parseDouble(data[1].trim());
                double rating = Double.parseDouble(data[2].trim());
                productList.add(new Product(product, price, rating));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productList;
    }

    private static <T> void mergeSort(List<T> list, Comparator<? super T> comparator) {
        if (list.size() > 1) {
            int mid = list.size() / 2;
            List<T> leftList = new ArrayList<>(list.subList(0, mid));
            List<T> rightList = new ArrayList<>(list.subList(mid, list.size()));

            mergeSort(leftList, comparator);
            mergeSort(rightList, comparator);

            int i = 0, j = 0, k = 0;
            while (i < leftList.size() && j < rightList.size()) {
                T left = leftList.get(i);
                T right = rightList.get(j);

                if (comparator.compare(left, right) <= 0) {
                    list.set(k, left);
                    i++;
                } else {
                    list.set(k, right);
                    j++;
                }
                k++;
            }

            while (i < leftList.size()) {
                list.set(k, leftList.get(i));
                i++;
                k++;
            }

            while (j < rightList.size()) {
                list.set(k, rightList.get(j));
                j++;
                k++;
            }
        }
    }
}
