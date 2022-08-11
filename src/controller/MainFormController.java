package controller;

import Inventory.Inventory;
import Inventory.Part;
import Inventory.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the main form of the inventory management system. FUTURE ENHANCEMENT The search function on this
 form could be extended to allow search's for all other information such as inventory level to see what parts and or
 products need to be restocked or perhaps being able to search by price range to build a more affordable product if
 there are multiple parts that could work.
 */
public class MainFormController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField MainFormPartsSearch;

    @FXML
    private TextField MainFormProductsSearch;

    @FXML
    private TableView<Part> MainPartsTable;

    @FXML
    private TableColumn<Part, Integer> MainPartID;

    @FXML
    private TableColumn<Part, Integer> MainPartInventory;

    @FXML
    private TableColumn<Part, String> MainPartName;

    @FXML
    private TableColumn<Part, Double> MainPartPrice;

    @FXML
    private TableView<Product> MainProductsTable;

    @FXML
    private TableColumn<Product, Integer> MainProductID;

    @FXML
    private TableColumn<Product, Integer> MainProductInventory;

    @FXML
    private TableColumn<Product, String> MainProductName;

    @FXML
    private TableColumn<Product, Double> MainProductPrice;


    @FXML
    void onActionAddParts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddProducts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }



    @FXML
    void onActionDeleteParts(ActionEvent event) {


        if (Inventory.getAllParts().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There are no parts to delete");
            alert.showAndWait();
        }

        else
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to delete the selected part?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {

                    int id = MainPartsTable.getSelectionModel().getSelectedItem().getId();
                    deletePart(id);
                }
            }
            catch(NullPointerException e)

                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Select the part you wish to delete");
                    alert.showAndWait();
                }

    }

    Product selectedProd;

    @FXML
    void onActionDeleteProducts(ActionEvent event) {

        selectedProd = MainProductsTable.getSelectionModel().getSelectedItem();

        if (Inventory.getAllProducts().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There are no products to delete");
            alert.showAndWait();
        }

        else
            try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to delete the selected product?");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (selectedProd.getAllAssociatedParts().isEmpty()) {
                            int id = MainProductsTable.getSelectionModel().getSelectedItem().getId();
                            deleteProd(id);
                        } else {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Remove all associated parts before deleting");
                            alert.showAndWait();
                        }
                    }
                }
            catch(NullPointerException e)
                {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Select the product you wish to delete");
                alert.showAndWait();
                }
    }


    @FXML
    void onActionExitApplication(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to exit the program?");
        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
            {
                System.exit(0);
            }

    }


    private static Part selectedPart;
    /** Getter for the selected part to be transferred to the modify parts controller. */
    public static Part getselectedPart()
    {
        return selectedPart;
    }
    @FXML
    void onActionModifyParts(ActionEvent event) throws IOException {

        selectedPart = MainPartsTable.getSelectionModel().getSelectedItem();

        if (Inventory.getAllParts().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("There are no parts to modify");
                alert.showAndWait();
            }

        else
           try
               {
                   FXMLLoader loader = new FXMLLoader();
                   loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
                   loader.load();

                   ModifyPartFormController MPFController = loader.getController();
                   MPFController.sendPart(MainPartsTable.getSelectionModel().getSelectedItem());

                   stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                   Parent scene = loader.getRoot();
                   stage.setScene(new Scene(scene));
                   stage.show();
               }

           catch(NullPointerException e)
                {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Error");
                   alert.setContentText("Select the part you wish to modify");
                   alert.showAndWait();
                }

    }



    private static Product selectedProduct;

    /** Getter for the selected product to be transferred to the modify products controller. */
    public static Product getSelectedProduct()
        {
            return selectedProduct;
        }

    @FXML
    void onActionModifyProducts(ActionEvent event) throws IOException {

         selectedProduct = MainProductsTable.getSelectionModel().getSelectedItem();

        if (Inventory.getAllProducts().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There are no products to modify");
            alert.showAndWait();
        }

        else
            try
                {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
                    loader.load();

                    ModifyProductFormController MProdController = loader.getController();
                    MProdController.sendProduct(MainProductsTable.getSelectionModel().getSelectedItem());;

                    stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                    Parent scene = loader.getRoot();
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select the product you wish to modify");
            alert.showAndWait();
        }

    }
    /** Searches the parts list by part name or ID number. Provides an alert if no results match the search. */
    public void mainPartsSearch(ActionEvent actionEvent) {
        String searchField = MainFormPartsSearch.getText();
        ObservableList<Part> parts = filter(searchField);

        if(parts.size() == 0)
            {
                try
                    {
                        int newID = Integer.parseInt(searchField);
                        Part part = search(newID);
                        if (part != null)
                        {
                            parts.add(part);
                        }
                    }
                catch(NumberFormatException e)
                {
                    //empty
                }
            }
        if(!(searchField.isEmpty()) && parts.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Parts match your search");
            Optional<ButtonType> result =  alert.showAndWait();
        }
        MainPartsTable.setItems(parts);
    }

    /** Searches the product list by product name or ID number. Provides an alert if no results match the search. */
    public void mainProductsSearch(ActionEvent actionEvent) {

        String searchField = MainFormProductsSearch.getText();
        ObservableList<Product> prod1 = filterProduct(searchField);

        if(prod1.size() == 0)
        {
            try {

                int newID = Integer.parseInt(searchField);
                Product prod = searchProd(newID);
                if (prod != null) {
                    prod1.add(prod);
                }

            }

            catch(NumberFormatException e)
            {
                //empty
            }
            if (!(searchField.isEmpty()) && prod1.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Products match your search");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }

        MainProductsTable.setItems(prod1);
    }

        /** Removes a part from the all parts list by matching ID number. */
    public boolean deletePart(int id){

        for(Part part1 : Inventory.getAllParts()) {

            if(part1.getId() == id){

                return Inventory.getAllParts().remove(part1);
            }
        }
        return false;
    }
    /** Removes a product from the all products list by matching ID number. */
    public boolean deleteProd(int id){

        for(Product prod1 : Inventory.getAllProducts()) {

            if(prod1.getId() == id){

                return Inventory.getAllProducts().remove(prod1);
            }
        }
        return false;
    }

    /** Creates a temporary parts list and adds a part if the entered search information is contained
     in any of the part names.  */
    public ObservableList<Part> filter (String part){

        if(!(Inventory.getFilteredParts().isEmpty()))
            Inventory.getFilteredParts().clear();

        for(Part part1 : Inventory.getAllParts()){
            if(part1.getName().contains(part))
                Inventory.getFilteredParts().add(part1);
        }

        return Inventory.getFilteredParts();

    }
    /** Creates a temporary products list and adds a product if the entered search information is contained
     in any of the product names. RUNTIME ERROR: Initially I had reused the parts search to re-create the
     product search but forgot to change the list that was being checked and cleared which meant the product
     search list was never cleared. I corrected this by changing the list that was checked and cleared to the
     filtered products list. */
    public ObservableList<Product> filterProduct (String prod1){

        if(!(Inventory.getFilteredProducts().isEmpty()))
            Inventory.getFilteredProducts().clear();

        for(Product prod : Inventory.getAllProducts()){
            if(prod.getName().contains(prod1))
                Inventory.getFilteredProducts().add(prod);
        }

        return Inventory.getFilteredProducts();

    }

    /** Searches the all parts list by ID number. */
    public Part search(int id){
        for(Part part : Inventory.getAllParts()){

            if(part.getId() == id)
                return part;
        }
        return null;
    }

    /** Searches the all products list by ID number. */
    public Product searchProd(int id){
        for(Product prod1 : Inventory.getAllProducts()){

            if(prod1.getId() == id)
                return prod1;
        }
        return null;
    }


    /** Initializes the main form and sets all cell values and populates the tables with all product and all parts. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainProductsTable.setItems(Inventory.getAllProducts());
        MainProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainProductInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        MainPartsTable.setItems(Inventory.getAllParts());
        MainPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));



    }

}
