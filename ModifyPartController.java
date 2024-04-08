package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import school.schoolproject.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainController.ParMod;

/** Contains the functionality for modifying parts */
public class ModifyPartController implements Initializable {
    public Button ModifyPartCancel;
    public RadioButton ModifyOutsourced;
    public javafx.scene.control.ToggleGroup ToggleGroup;
    public RadioButton ModifyInHouse;
    public Label ChangeText;
    public Button ModifyPartSave;
    public TextField ModifyPartMax;
    public TextField ModifyPartMin;
    public TextField ModifyPartPrice;
    public TextField ModifyPartInv;
    public TextField ModifyPartName;
    public TextField ModifyPartID;
    public TextField ModifyCompanyOrMachine;
    private static Part PartTransfer = null;

    /** Allows for part data to transfer from main screen */
    public static void PartTransfer(Part transfer){
        PartTransfer = transfer;
    }

    /** Start code */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Part ParMod = MainController.GetSelectedPart(PartTransfer);

        if (ParMod instanceof InHouse){
            ChangeText.setText("Machine ID");
            ModifyInHouse.setSelected(true);
            ModifyCompanyOrMachine.setText(String.valueOf(((InHouse)ParMod).getMachineID()));
        } else if (ParMod instanceof Outsourced) {
            ChangeText.setText("Company Name");
            ModifyOutsourced.setSelected(true);
            ModifyCompanyOrMachine.setText(((Outsourced) ParMod).getCompanyName());
        }

        ModifyPartID.setText(String.valueOf(ParMod.getId()));
        ModifyPartName.setText(String.valueOf(ParMod.getName()));
        ModifyPartInv.setText(String.valueOf(ParMod.getStock()));
        ModifyPartPrice.setText(String.valueOf(ParMod.getPrice()));
        ModifyPartMax.setText(String.valueOf(ParMod.getMax()));
        ModifyPartMin.setText(String.valueOf(ParMod.getMin()));
    }

    /** Modify part cancel button */
    public void OnModifyPartCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** In-House radio button */
    public void OnInHouse(ActionEvent actionEvent) {
        ChangeText.setText("Machine ID");
    }

    /** Outsourced radio button */
    public void OnOutsourced(ActionEvent actionEvent) {
        ChangeText.setText("Company Name");
    }

    /** Modify part save button */
    public void OnModifyPartSave(ActionEvent actionEvent) {
        try {
            int PartID = Integer.parseInt(ModifyPartID.getText());
            String PartName = ModifyPartName.getText();
            int PartInv = Integer.parseInt(ModifyPartInv.getText());
            double PartPrice = Double.parseDouble(ModifyPartPrice.getText());
            int PartMin = Integer.parseInt(ModifyPartMin.getText());
            int PartMax = Integer.parseInt(ModifyPartMax.getText());

            int MachineID;
            String CompanyName;

            if (PartName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setContentText("Must enter Part Name");
                alert.showAndWait();
                return;
            } else if (PartMin > PartMax) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setContentText("Part Minimum must be less than Part Maximum.");
                alert.showAndWait();
                return;
            } else if(PartInv>PartMax || PartInv<PartMin) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setContentText("Part Inventory must be between Part Minimum and Maximum.");
                alert.showAndWait();
                return;
            } else {
                if (ModifyInHouse.isSelected()) {
                    MachineID = Integer.parseInt(ModifyCompanyOrMachine.getText());
                    Part NewPart = new InHouse(PartID, PartName, PartPrice, PartInv, PartMin, PartMax, MachineID);
                    Inventory.RemovePart(ParMod);
                    Inventory.AddPart(NewPart);
                } else if (ModifyOutsourced.isSelected()){
                    CompanyName = ModifyCompanyOrMachine.getText();
                    Part NewPart = new Outsourced(PartID, PartName, PartPrice, PartInv, PartMin, PartMax, CompanyName);
                    Inventory.RemovePart(ParMod);
                    Inventory.AddPart(NewPart);
                }
            }
            Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Input Error");
            alert.showAndWait();
            return;
        }
    }
}
