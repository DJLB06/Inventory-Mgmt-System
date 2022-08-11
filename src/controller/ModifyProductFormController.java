package controller;


import Inventory.Inventory;
import Inventory.Product;
import Inventory.Part;
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

/** This class controls the modify product form. FUTURE ENHANCEMENT. A link could be added from this form to the add
 part form. There may come a time when someone is adding parts and products and they forgot to add parts first or
 missed adding a part that they need to add to a specific product. The link would take the user to the add part form
 where they can add parts and when they are done it will take them back to the modify product form with their previous
 information saved and the new part/parts will be available to be added.
 */
public class ModifyProductFormController implements Initializable {

    Stage stage;
    Parent scene;
    private ObservableList<Part> associatedParts  = FXCollections.observableArrayList();

    @FXML
    private TextField ModifyProdIDTxt;

    @FXML
    private TextField ModifyProdInvTxt;

    @FXML
    private TextField ModifyProdMaxTxt;

    @FXML
    private TextField ModifyProdMinTxt;

    @FXML
    private TextField ModifyProdNameTxt;

    @FXML
    private TextField ModifyProdPriceTxt;

    @FXML
    private TextField ModifyProdSearchBar;

    @FXML
    private TableView<Part> ModifyProdTableOne;

    @FXML
    private TableView<Part> ModifyProdTableTwo;

    @FXML
    private TableColumn<Part, Integer> AddProductInventoryOne;

    @FXML
    private TableColumn<Part, Integer> AddProductInventoryTwo;

    @FXML
    private TableColumn<Part, Integer> AddProductPartIDOne;

    @FXML
    private TableColumn<Part, Integer> AddProductPartIDTwo;

    @FXML
    private TableColumn<Part, String> AddProductPartNameOne;

    @FXML
    private TableColumn<Part, String> AddProductPartNameTwo;

    @FXML
    private TableColumn<Part, Double> AddProductPriceOne;

    @FXML
    private TableColumn<Part, Double> AddProductPriceTwo;

    //Action Events


    @FXML
    void onActionAddModifyProduct(ActionEvent event) {

        Part selectedPart = ModifyProdTableOne.getSelectionModel().getSelectedItem();
        Product.addAssociatedPartTemp(selectedPart);
        ModifyProdTableTwo.setItems(Product.getAssociatedPartsTemp());

    }

    @FXML
    void onActionCancelModifyProductForm(ActionEvent event) throws IOException {

        Product.getAssociatedPartsTemp().clear();

        ModifyProdTableTwo.setItems(associatedParts);

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionRemoveAssociatePartModifyForm(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove the associated part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Part selectedPart = ModifyProdTableTwo.getSelectionModel().getSelectedItem();
            Product.removeAssociatedPartTemp(selectedPart);
            ModifyProdTableTwo.setItems(Product.getAssociatedPartsTemp());
        }
    }


    @FXML
    void onActionSaveModifyProductForm(ActionEvent event) throws IOException {

        String name = ModifyProdNameTxt.getText();
        int stock = Integer.parseInt(ModifyProdInvTxt.getText());
        double price = Double.parseDouble(ModifyProdPriceTxt.getText());
        int max = Integer.parseInt(ModifyProdMaxTxt.getText());
        int min = Integer.parseInt(ModifyProdMinTxt.getText());

        selectedProduct.setName(name);
        selectedProduct.setStock(stock);
        selectedProduct.setPrice(price);
        selectedProduct.setMax(max);
        selectedProduct.setMin(min);

        selectedProduct.getAllAssociatedParts().clear();
        for(Part part : Product.getAssociatedPartsTemp())
            {
                selectedProduct.addAssociatedPart(part);
            }

        Product.getAssociatedPartsTemp().clear();

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


/** Sends the necessary information between the main form controller and the modify product form controller. RUNTIME ERROR Initially
  I struggled with passing the associated parts list and having it populate in the second tableview. This was corrected
 by moving the setItems function from the modify product form intializer to the sendproduct method. */
   public void sendProduct(Product product) {

       ModifyProdIDTxt.setText(String.valueOf(product.getId()));
       ModifyProdInvTxt.setText(String.valueOf(product.getStock()));
       ModifyProdNameTxt.setText(product.getName());
       ModifyProdPriceTxt.setText(String.valueOf(product.getPrice()));
       ModifyProdMinTxt.setText(String.valueOf(product.getMin()));
       ModifyProdMaxTxt.setText(String.valueOf(product.getMax()));
       associatedParts = product.getAllAssociatedParts();
       selectedProduct = MainFormController.getSelectedProduct();

       for(Part part : product.getAllAssociatedParts())
       {
           Product.addAssociatedPartTemp(part);

       }


       ModifyProdTableTwo.setItems(associatedParts);

   }

    /** Searches the all parts list by name or ID number. Creates an alert if the information entered doesn't match
     any parts.  */
    public void ModifyProdSearch(ActionEvent actionEvent) {

        String searchField = ModifyProdSearchBar.getText();
        ObservableList<Part> part = filter(searchField);

        if(part.size() == 0)
        {
            try
            {

                int newID = Integer.parseInt(searchField);
                Part part1 = search(newID);
                if (part1 != null)
                {
                    part.add(part1);
                }
            }

            catch(NumberFormatException e)
            {
                //empty
            }
            if (!(searchField.isEmpty()) && part.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Parts match your search");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
        ModifyProdTableOne.setItems(part);

    }

    /** Filters the all parts list by seeing if the information passed is contained in any part name. */
    public ObservableList<Part> filter (String part){

        if(!(Inventory.getFilteredParts().isEmpty()))
            Inventory.getFilteredParts().clear();

        for(Part part1 : Inventory.getAllParts()){
            if(part1.getName().contains(part))
                Inventory.getFilteredParts().add(part1);
        }

        return Inventory.getFilteredParts();
    }

    /** Searches the all parts list by ID. */
    public Part search(int id){
        for(Part part : Inventory.getAllParts()){

            if(part.getId() == id)
                return part;
        }
        return null;
    }

    Product selectedProduct;

    /** Initializes the Modify Product form and sets all cell values and populates table one with the all parts list.
     RUNTIME ERROR
     When testing my application the remove associated part button would work for some items and not for others.
     It turned out that I had entered the wrong table in the line of code that assigned the selected part to be
     removed from the second table. This meant that I was actually only able to delete parts that matched the item
     I had selected in the first table (usually the part I had added last). The item I selected in the bottom table
     actually had no effect on what I was removing. I corrected this by changing the selected item to be taken from
     the second table.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ModifyProdTableOne.setItems(Inventory.getAllParts());

        AddProductPartIDOne.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductPartNameOne.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductInventoryOne.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductPriceOne.setCellValueFactory(new PropertyValueFactory<>("price"));

        AddProductPartIDTwo.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductPartNameTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductInventoryTwo.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductPriceTwo.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
