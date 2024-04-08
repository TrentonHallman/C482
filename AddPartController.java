package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import school.schoolproject.model.InHouse;
import school.schoolproject.model.Inventory;
import school.schoolproject.model.Outsourced;

import java.io.IOException;

/** Contains the functionality for adding parts */
public class AddPartController {

    public RadioButton AddOutsourced;
    public RadioButton AddInHouse;
    public Button AddPartCancel;
    public javafx.scene.control.ToggleGroup ToggleGroup;
    public Label ChangeText;
    public TextField AddPartName;
    public TextField AddPartInv;
    public TextField AddPartPrice;
    public TextField AddPartMin;
    public TextField AddPartMax;
    public TextField AddPartID;
    public Button SavePart;
    public TextField AddCompanyOrMachine;
    int PartID = 1;

    /** Add part cancel button  */
    public void OnAddPartCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** In-House radio button  */
    public void OnInHouse(ActionEvent actionEvent) {
        ChangeText.setText("Machine ID");
    }

    /** Outsourced radio button  */
    public void OnOutsourced(ActionEvent actionEvent) {
        ChangeText.setText("Company Name");
    }

    /** Save part button  */
    public void OnSavePart(ActionEvent actionEvent) throws IOException{
        try {
            PartID = (int) (Math.random() * 100);
            String PartName = AddPartName.getText();
            int PartInv = Integer.parseInt(AddPartInv.getText());
            double PartPrice = Double.parseDouble(AddPartPrice.getText());
            int PartMin = Integer.parseInt(AddPartMin.getText());
            int PartMax = Integer.parseInt(AddPartMax.getText());

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
                if (AddInHouse.isSelected()){
                    MachineID = Integer.parseInt(AddCompanyOrMachine.getText());
                    Inventory.AddPart(new InHouse(PartID,PartName, PartPrice, PartInv, PartMin, PartMax, MachineID));
                } else if (AddOutsourced.isSelected()){
                    CompanyName = AddCompanyOrMachine.getText();
                    Inventory.AddPart(new Outsourced(PartID,PartName, PartPrice, PartInv, PartMin, PartMax, CompanyName));
                }

                Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Input Error");
            alert.showAndWait();
            return;
        }
    }
}
