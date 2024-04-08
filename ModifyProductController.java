package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import school.schoolproject.model.Inventory;
import school.schoolproject.model.Part;
import school.schoolproject.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.MainController.ProMod;
import static school.schoolproject.model.Inventory.PartInventory;

/** Contains the functionality for modifying products */
public class ModifyProductController implements Initializable {
    public Button ModifyProductCancel;
    public TableColumn AssocPartsIDCol;
    public TableColumn AssocPartsNameCol;
    public TableColumn AssocPartsInventoryCol;
    public TableColumn AssocPartsPriceCol;
    public TableView<Part> AssocPartsTable;
    public TableColumn partsPriceCol;
    public TableColumn partsInventoryCol;
    public TableColumn partsNameCol;
    public TableColumn partsIDCol;
    public TableView<Part> partsTable;
    public Button AddPartButton;
    public Button RemovePart;
    public TextField PartsSearch;
    public TextField ModifyProductID;
    public TextField ModifyProductName;
    public TextField ModifyProductInv;
    public TextField ModifyProductPrice;
    public TextField ModifyProductMax;
    public TextField ModifyProductMin;
    public Button ModifyProductSave;
    private ObservableList<Part> AssociatePartsList = FXCollections.observableArrayList();
    private static Product ProductTransfer = null;

    /** Allows for product data to transfer from main screen */
    public static void ProductTransfer(Product transfer){
        ProductTransfer = transfer;
    }

    /** Start code */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Product ProMod = MainController.GetSelectedProduct(ProductTransfer);
        AssociatePartsList = ProMod.GetAssociatedParts();

        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.GetPartInventory());

        AssocPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssocPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssocPartsTable.setItems(AssociatePartsList);

        ModifyProductID.setText(String.valueOf(ProMod.getId()));
        ModifyProductName.setText((ProMod.getName()));
        ModifyProductInv.setText(String.valueOf(ProMod.getStock()));
        ModifyProductPrice.setText(String.valueOf(ProMod.getPrice()));
        ModifyProductMax.setText(String.valueOf(ProMod.getMax()));
        ModifyProductMin.setText(String.valueOf(ProMod.getMin()));
    }

    /** Parts search functionality */
    public void OnPartsSearch(ActionEvent actionEvent) {
        String searchText = PartsSearch.getText();
        ObservableList<Part> partsResult = FXCollections.observableArrayList();

        for (Part part : PartInventory){
            if(String.valueOf(part.getId()).contains(searchText) ||
                    part.getName().contains(searchText)){
                partsResult.add(part);
            }
        }
        partsTable.setItems(partsResult);
        PartsSearch.setText("");
    }

    /** Modify product cancel button */
    public void OnModifyProductCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** Add associated part button */
    public void OnAddPartButton(ActionEvent actionEvent) {
        Part SPar = partsTable.getSelectionModel().getSelectedItem();
        if (SPar == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Must Select a Part.");
            alert.showAndWait();
            return;
        } else {
        AssociatePartsList.add(SPar);
        AssocPartsTable.setItems(AssociatePartsList);
        }
    }

    /** Remove associated part button*/
    public void OnRemovePart(ActionEvent actionEvent) {
        Part SPar = AssocPartsTable.getSelectionModel().getSelectedItem();
        if(SPar == null) {
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to remove this part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                AssociatePartsList.remove(SPar);
            }
        }
    }

    /** Modify product save button*/
    public void OnModifyProductSave(ActionEvent actionEvent) throws IOException{
        try {
            int ProductID = Integer.parseInt(ModifyProductID.getText());
            String ProductName = ModifyProductName.getText();
            int ProductInv = Integer.parseInt(ModifyProductInv.getText());
            double ProductPrice = Double.parseDouble(ModifyProductPrice.getText());
            int ProductMin = Integer.parseInt(ModifyProductMin.getText());
            int ProductMax = Integer.parseInt(ModifyProductMax.getText());
            AssociatePartsList = AssocPartsTable.getItems();

            if (ProductName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setContentText("Must enter Product Name");
                alert.showAndWait();
                return;
            } else if (ProductMin > ProductMax) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setContentText("Product Minimum must be less than Product Maximum.");
                alert.showAndWait();
                return;
            } else if(ProductInv>ProductMax || ProductInv<ProductMin){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setContentText("Product Inventory must be between Product Minimum and Maximum.");
                alert.showAndWait();
                return;
            } else {
                Product NewProduct = new Product(ProductID,ProductName,ProductPrice,ProductInv,ProductMin,ProductMax);

                for (Part part: AssociatePartsList) {
                    NewProduct.addAssociatedParts(part);
                }

                Inventory.RemoveProduct(ProMod);
                Inventory.AddProduct(NewProduct);

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
