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
import school.schoolproject.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static school.schoolproject.model.Inventory.PartInventory;

/** Contains the functionality for adding products */
public class AddProductController implements Initializable {

    public Button AddProductCancel;
    public TextField AddProductName;
    public TextField AddProductInv;
    public TextField AddProductPrice;
    public TextField AddProductMin;
    public TextField AddProductMax;
    public TableColumn<Object, Object> partsIDCol;
    public TableColumn<Object, Object> partsNameCol;
    public TableColumn<Object, Object> partsInventoryCol;
    public TableColumn<Object, Object> partsPriceCol;
    public TableView<Part> AssocPartsTable;
    public TableColumn<Object, Object> AssocPartsIDCol;
    public TableColumn<Object, Object> AssocPartsNameCol;
    public TableColumn<Object, Object> AssocPartsInventoryCol;
    public TableColumn<Object, Object> AssocPartsPriceCol;
    public TextField PartsSearch;
    public Button AddPartButton;
    public TableView<Part> partsTable;
    public Button RemovePart;
    int ProductID = 1;
    private ObservableList<Part> AssociatePartsList = FXCollections.observableArrayList();

    /** Start code */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }

    /** Add product cancel button */
    public void OnAddProductCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** Add product save button */
    public void OnSaveProduct (ActionEvent actionEvent) throws IOException{
        try {
            ProductID = (int) (Math.random() * 1000);
            String ProductName = AddProductName.getText();
            int ProductInv = Integer.parseInt(AddProductInv.getText());
            double ProductPrice = Double.parseDouble(AddProductPrice.getText());
            int ProductMin = Integer.parseInt(AddProductMin.getText());
            int ProductMax = Integer.parseInt(AddProductMax.getText());

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
                alert.setContentText("Product Maximum must be less than Product Inventory.");
                alert.showAndWait();
                return;
            }
            
            Product NewProduct = new Product(ProductID,ProductName, ProductPrice, ProductInv, ProductMin, ProductMax);
            Inventory.AddProduct(NewProduct);

            for (Part part: AssociatePartsList) {
                NewProduct.addAssociatedParts(part);
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

    /** Add associated part button */
    public void OnAddPartButton(ActionEvent actionEvent) {
        Part SPar = partsTable.getSelectionModel().getSelectedItem();
        if(SPar == null)
            return;
        AssociatePartsList.add(SPar);
    }

    /** Delete part button */
    public void OnRemovePart(ActionEvent actionEvent) {
        Part SPar = AssocPartsTable.getSelectionModel().getSelectedItem();
        if(SPar == null)
            return;
        AssociatePartsList.remove(SPar);
    }
}
