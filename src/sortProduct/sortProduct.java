package sortProduct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    public static void main(String[] args) {
        String filePath = "product.txt"; // Replace with the actual file path

        List<Product> productList = readDataFromFile(filePath);
        if (productList != null) {
            mergeSort(productList, 0, productList.size() - 1);

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

    private static void mergeSort(List<Product> productList, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(productList, left, mid);
            mergeSort(productList, mid + 1, right);
            merge(productList, left, mid, right);
        }
    }

    private static void merge(List<Product> productList, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Product> leftList = new ArrayList<>();
        List<Product> rightList = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            leftList.add(productList.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightList.add(productList.get(mid + 1 + j));
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftList.get(i).price <= rightList.get(j).price) {
                productList.set(k, leftList.get(i));
                i++;
            } else {
                productList.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            productList.set(k, leftList.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            productList.set(k, rightList.get(j));
            j++;
            k++;
        }
    }
}
