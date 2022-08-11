package controller;

import Inventory.Inventory;
import Inventory.OutSourced;
import Inventory.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Inventory.InHouse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls the modify part form. FUTURE ENHANCEMENT A good enhancement that could be added in the future
 would be to create a list on this page of all the products that are currently associated with the part. This would help
 employee's see which products are going to be effected if a part is unavailable.
 */
public class ModifyPartFormController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private RadioButton iModifyPartRbtn;

    @FXML
    private TextField oModifyPartIDTxt;

    @FXML
    private TextField oModifyPartInvTxt;

    @FXML
    private TextField oModifyPartMachineIDTxt;

    @FXML
    private TextField oModifyPartMaxTxt;

    @FXML
    private TextField oModifyPartMinTxt;

    @FXML
    private TextField oModifyPartNameTxt;

    @FXML
    private TextField oModifyPartPriceTxt;

    @FXML
    private RadioButton oModifyPartRbtn;

    @FXML
    private Label ModifyPartMachineID;

    //Action Events

    @FXML
    void onActionCancelModifyPartForm(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSaveModifyPartForm(ActionEvent event) throws IOException {

        try {
            int id = selectedPart.getId();
            String name = oModifyPartNameTxt.getText();
            int stock = Integer.parseInt(oModifyPartInvTxt.getText());
            double price = Double.parseDouble(oModifyPartPriceTxt.getText());
            int max = Integer.parseInt(oModifyPartMaxTxt.getText());
            int min = Integer.parseInt(oModifyPartMinTxt.getText());

            if (Inventory.checkMinMax(min, max, stock)) {

                deletePart(id);

                if (iModifyPartRbtn.isSelected()) {
                    int machineID = Integer.parseInt(oModifyPartMachineIDTxt.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                }
                if (oModifyPartRbtn.isSelected()) {
                    String companyName = oModifyPartMachineIDTxt.getText();
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
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each field");
            alert.showAndWait();
        }
    }


    /** Sets the text to Company Name when the outsourced radio button is selected. */
    @FXML
    public void addPartOnOutsourced(ActionEvent actionEvent) {
        ModifyPartMachineID.setText("Company Name");

    }

    /** Sets the text to Machine ID when the inhouse radio button is selected. */
    @FXML
    public void addPartOnInhouse(ActionEvent actionEvent) {
        ModifyPartMachineID.setText("Machine ID");
    }

    Part selectedPart;
    /** Transfers the necessary information between the main form controller and the modify part form controller. RUNTIME
     ERROR: Initially I was having difficulty transferring the selected part as an entire entity, all information
     would correctly pass and populate but updating the stored part would not work. I corrected this by creating a
     getter in the main form and a selected part variable in the sendpart method to pass the Part as a whole and not
     just its associated ID, Name, etc values. . */
    public void sendPart(Part part){

        oModifyPartIDTxt.setText(String.valueOf(part.getId()));
        oModifyPartNameTxt.setText(part.getName());
        oModifyPartInvTxt.setText(String.valueOf(part.getStock()));
        oModifyPartPriceTxt.setText(String.valueOf(part.getPrice()));
        oModifyPartMinTxt.setText(String.valueOf(part.getMin()));
        oModifyPartMaxTxt.setText(String.valueOf(part.getMax()));
        selectedPart = MainFormController.getselectedPart();

        if(part instanceof InHouse){

            oModifyPartMachineIDTxt.setText(String.valueOf(((InHouse) part).getMachineID()));
            iModifyPartRbtn.setSelected(true);
        }
        if(part instanceof OutSourced){

            oModifyPartMachineIDTxt.setText(String.valueOf(((OutSourced) part).getCompanyName()));
            oModifyPartRbtn.setSelected(true);
        }

    }

    /** Deletes a part from the all parts list based on ID. */
    public boolean deletePart(int id){

        for(Part part1 : Inventory.getAllParts()) {

            if(part1.getId() == id){

                return Inventory.getAllParts().remove(part1);
            }
        }
        return false;
    }

    /** Initializes the Modify Part Form controller.  */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
