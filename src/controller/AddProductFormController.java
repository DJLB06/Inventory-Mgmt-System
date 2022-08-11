package controller;

import Inventory.Inventory;
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
import Inventory.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the add product form. */
public class AddProductFormController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField AddProdIDTxt;

    @FXML
    private TextField AddProdInvTxt;

    @FXML
    private TextField AddProdMaxTxt;

    @FXML
    private TextField AddProdMinTxt;

    @FXML
    private TextField AddProdNameTxt;

    @FXML
    private TextField AddProdPriceTxt;

    @FXML
    private TextField AddProdSearchBar;

    @FXML
    private TableView<Part> AddProdTableOne;

    @FXML
    private TableView<Part> AddProdTableTwo;

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

    /** Adds the selected part to a temporary associated parts list and populates it in the associated parts
     table. */
    @FXML
    void onActionAddProduct(ActionEvent event) {

        Part selectedPart = AddProdTableOne.getSelectionModel().getSelectedItem();
        Product.addAssociatedPartTemp(selectedPart);

        AddProdTableTwo.setItems(Product.getAssociatedPartsTemp());

    }

    /** Leaves the add product form and returns to the main form. */

    @FXML
    void onActionCancelAddProductForm(ActionEvent event) throws IOException {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your information will not be saved, do you want to continue?");

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

            Product.getAssociatedPartsTemp().clear();
        }


    }
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    @FXML
    void onActionSaveAddProductForm(ActionEvent event) throws IOException {

    try {
        int id = Inventory.setProductId();
        String name = AddProdNameTxt.getText();
        int stock = Integer.parseInt(AddProdInvTxt.getText());
        double price = Double.parseDouble(AddProdPriceTxt.getText());
        int max = Integer.parseInt(AddProdMaxTxt.getText());
        int min = Integer.parseInt(AddProdMinTxt.getText());


        Product prod = new Product(id, name, price, stock, min, max);

        for (Part part : Product.getAssociatedPartsTemp()) {
            prod.addAssociatedPart(part);

        }

        Inventory.addProduct(prod);

        Product.getAssociatedPartsTemp().clear();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each field");
            alert.showAndWait();
        }

    }


    @FXML
    void onRemoveAssociatedPartAddProductForm(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove the associated part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

        Part selectedPart = AddProdTableTwo.getSelectionModel().getSelectedItem();
        Product.removeAssociatedPartTemp(selectedPart);

        AddProdTableTwo.setItems(Product.getAssociatedPartsTemp());
        }

    }

    /** Controls the search bar. Filters by ID or name and if the search bar is empty the original list returns.
     If nothing matches the search the user is alerted.  */
    public void addProductSearch(ActionEvent actionEvent) {

        String searchField = AddProdSearchBar.getText();
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
        AddProdTableOne.setItems(part);
    }

/** Searches for a part by ID number. */
    public Part search(int id){
        for(Part part : Inventory.getAllParts()){

            if(part.getId() == id)
                return part;
        }
        return null;
    }


    /** Looks through the all parts list by part name and adds it to a temporary list to return for a search. RUNTIME ERROR.
     *Initially when conducting the search it would work fine however if I returned and ran the search
     again it would populate with the old information that had been search before. This was corrected by checking the
     list and if it was not empty it would be cleared before the new search parts could be added. */
    public ObservableList<Part> filter (String part){

        if(!(Inventory.getFilteredParts().isEmpty()))
            Inventory.getFilteredParts().clear();

        for(Part part1 : Inventory.getAllParts()){
            if(part1.getName().contains(part))
                Inventory.getFilteredParts().add(part1);
        }

        return Inventory.getFilteredParts();
    }


    /** Initializes the Add Product form and sets the table cell values and populates the top table with all
      parts. FUTURE ENHANCEMENT A future field could be added that automatically adds
     together the price of all the associated parts as you add or remove them from the associated parts list so the
     user can see the total value of all parts associated with the product. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AddProdTableOne.setItems(Inventory.getAllParts());

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
