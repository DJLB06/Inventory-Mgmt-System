package Inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Inventory class for all parts and products. */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    private static int partId;
    private static int productId;

    /** Creates a unique part ID when a part is added. */
    public static int setPartID() {
        partId = ++partId;
        return partId;
    }
    /** Creates a unique product ID when a product is added. */
    public static int setProductId() {
        productId = ++productId;
        return productId;
    }

    /** Adds a part to the all parts list. */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /** Returns the all parts list. */
    public static ObservableList<Part> getAllParts(){

        return allParts;
    }

    /** Returns the filtered parts list. */
    public static ObservableList<Part> getFilteredParts(){

        return filteredParts;
    }



    /** Adds a product to the all products list. */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /** Returns the all products list. */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    /** Returns the filtered products list. */
    public static ObservableList<Product> getFilteredProducts(){

        return filteredProducts;
    }

    /** Checks that the information entered for minimum is lower than the maximum and that inventory is somewhere between
     the minimum and the maximum values. */
    public static boolean checkMinMax(int min, int max, int inv) {

        if (min < max && inv >= min && inv <= max){
            return true;
        }
        return false;
    }



}
