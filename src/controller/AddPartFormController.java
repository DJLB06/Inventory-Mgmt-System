package controller;

import Inventory.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Inventory.InHouse;
import Inventory.OutSourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the add part form controller. FUTURE ENHANCEMENT. Depending on how large the inventory currently
 is or may become it could become beneficial to add additional fields for the location of the part in the
 warehouse/inventory area such as aisle number or section ID to make it easier to locate.
 */

public class AddPartFormController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton iAddPartRbtn;

    @FXML
    private TextField oAddPartIDTxt;

    @FXML
    private TextField oAddPartInvTxt;

    @FXML
    private TextField oAddPartMachineIDTxt;

    @FXML
    private TextField oAddPartMaxTxt;

    @FXML
    private TextField oAddPartMinTxt;

    @FXML
    private TextField oAddPartNameTxt;

    @FXML
    private TextField oAddPartPriceTxt;

    @FXML
    private RadioButton oAddPartRbtn;

    @FXML
    private Label AddPartMachineID;

    /** Leaves the add part form and returns to the main form. RUNTIME ERROR: originally the result of the selected
     button was not checked so both okay and cancel would result in confirmation and return to the main form. This
     was corrected by adding a check if the Okay button was selected prior to continuing back to the main form. */
    @FXML
    void onActionCancelAddPart(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your information will not be saved, do you want to continue?");

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /** Saves the information entered on the form as a new part after checking to make sure all the fields have
      been entered correctly. */
    @FXML
    void onActionSaveAddPart(ActionEvent event) throws IOException {

      try{
          int id = Inventory.setPartID();
          String name = oAddPartNameTxt.getText();
          int stock = Integer.parseInt(oAddPartInvTxt.getText());
          double price = Double.parseDouble(oAddPartPriceTxt.getText());
          int max = Integer.parseInt(oAddPartMaxTxt.getText());
          int min = Integer.parseInt(oAddPartMinTxt.getText());

          if(Inventory.checkMinMax(min,max,stock)) {


              if (iAddPartRbtn.isSelected()) {
                  int machineID = Integer.parseInt(oAddPartMachineIDTxt.getText());
                  Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
              } else if (oAddPartRbtn.isSelected()) {
                  String companyName = oAddPartMachineIDTxt.getText();
                  Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName));
              }


              stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
              scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
              stage.setScene(new Scene(scene));
              stage.show();

          }
          else
          {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error Dialogue");
              alert.setContentText("Please enter valid Min, Max, and Inv amounts");
              alert.showAndWait();
          }
      }

      catch(NumberFormatException e){

         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error Dialogue");
         alert.setContentText("Please enter a valid value for each field");
         alert.showAndWait();

      }

    }

    /** Changes text to Company Name when outsourced raido button is selected. */
    public void AddPartOnOutsourced(ActionEvent actionEvent) {
        AddPartMachineID.setText("Company Name");

    }

    /** Changes text to Machine ID when inhouse radio button is selected. */
    public void AddPartOnInhouse(ActionEvent actionEvent) {
        AddPartMachineID.setText("Machine ID");
    }

    /** Initializes the add part form controller. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
