package Inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

    /** Creates the public product class expected variables, constructor, associated parts, getters, and setters. */
public class Product {

    private static ObservableList<Part> associatedPartsTemp = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

        /** Product constructor. */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

        /** Returns product ID. */
    public int getId() {
        return id;
    }

        /** Sets product ID. */
    public void setId(int id) {
        this.id = id;
    }

        /** Returns product name. */
    public String getName() {
        return name;
    }

        /** Sets product name. */
    public void setName(String name) {
        this.name = name;
    }
        /** Returns product price. */
    public double getPrice() {
        return price;
    }

        /** Sets product price. */
    public void setPrice(double price) {
        this.price = price;
    }

        /** Returns product inventory/stock. */
    public int getStock() {
        return stock;
    }

        /** Sets product inventory/stock. */
    public void setStock(int stock) {
        this.stock = stock;
    }

        /** Returns product min. */
    public int getMin() {
        return min;
    }

        /** Sets product min. */
    public void setMin(int min) {
        this.min = min;
    }

        /** Returns product max. */
    public int getMax() {
        return max;
    }

        /** Sets product max. */
    public void setMax(int max) {
        this.max = max;
    }

        /** Adds part to the products associated parts list. */
    public void addAssociatedPart(Part part){

       associatedParts.add(part);
    }

        /** Deletes part from the products associated parts list. */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
       else
        return false;

    }

        /** Returns the associated parts list. */
    public ObservableList<Part> getAllAssociatedParts(){

        return associatedParts;
    }


        /** Adds part to the products temporary associated parts list. */
    public static void addAssociatedPartTemp(Part part){
        associatedPartsTemp.add(part);
    }

        /** Returns the products temporary associated parts list. */
    public static ObservableList<Part> getAssociatedPartsTemp(){
        return associatedPartsTemp;
    }
        /** Removes a part from the products temporary associated parts list. */
    public static void removeAssociatedPartTemp(Part part){
        associatedPartsTemp.remove(part);
    }
}
