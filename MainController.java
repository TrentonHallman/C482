package controller;

import school.schoolproject.model.Inventory;
import school.schoolproject.model.Part;
import school.schoolproject.model.Product;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static school.schoolproject.model.Inventory.*;

/** Contains the functionality for the main screen of the application */
public class MainController implements Initializable {
    public TableView<Part> partsTable;
    public TableColumn<Object, Object> partsIDCol;
    public TableColumn<Object, Object> partsNameCol;
    public TableColumn<Object, Object> partsInventoryCol;
    public TableColumn<Object, Object> partsPriceCol;
    public Button AddPart;
    public Button ModifyPart;
    public Button DeletePart;
    public TableView<Product> productTable;
    public TableColumn<Object, Object> productsIDCol;
    public TableColumn<Object, Object> productsNameCol;
    public TableColumn<Object, Object> productsInventoryCol;
    public TableColumn<Object, Object> productsPriceCol;
    public Button AddProduct;
    public Button ModifyProduct;
    public Button DeleteProduct;
    public TextField PartsSearch;
    public TextField ProductsSearch;
    public Button ExitButton;
    static Part ParMod;
    static Product ProMod;

    /** Get selected product function */
    public static Product GetSelectedProduct(Product productTransfer) {
        return ProMod;
    }

    /** Get selected part function */
    public static Part GetSelectedPart(Part partTransfer) {
        return ParMod;
    }

    /** Add part functionality
     * RUNTIME ERROR:
     * I had an issue with loading other screens, I resolved this issue by
     * adding a "/" to the front of all of my screen loading code.
     * */
    public void OnAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AddPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /** Add product functionality */
    public void OnAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /** Modify part button */
    public void OnModifyPart(ActionEvent actionEvent) throws IOException {

        ParMod = partsTable.getSelectionModel().getSelectedItem();
        ModifyPartController.PartTransfer(ParMod);
        if (ParMod == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Must select a Part to be modified.");
            alert.showAndWait();
            return;
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Modify product button */
    public void OnModifyProduct(ActionEvent actionEvent) throws IOException {
            ProMod = productTable.getSelectionModel().getSelectedItem();
            ModifyProductController.ProductTransfer(ProMod);
        if (ProMod == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Must select a Product to be modified.");
            alert.showAndWait();
            return;
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Delete part button */
    public void OnDeletePart(ActionEvent actionEvent) {
        Part SPar = partsTable.getSelectionModel().getSelectedItem();
        if(SPar == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Must select a Part to be deleted.");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                Inventory.RemovePart(SPar);
            }
        }
    }

    /** Delete product button
     * FUTURE ENHANCEMENT:
     * My idea for a future enhancement would be to give users another prompt when deleting
     * a product with associated parts, which would ask them if they wanted to remove associated parts
     * and delete the product anyway. Having to go back in and modify a product to remove associate parts,
     * seems like a hassle to me.
     * */
    public void OnDeleteProduct(ActionEvent actionEvent) {

        Product SPro = productTable.getSelectionModel().getSelectedItem();

        if(SPro == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Must select a Product to be deleted.");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to remove this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> AssociatedParts = SPro.GetAssociatedParts();

                if (AssociatedParts.size() != 0) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("Selected Product has Associated Parts.");
                    alert.showAndWait();
                    return;
                } else {
                    Inventory.RemoveProduct(SPro);
                }
            }
        }
    }

    /** Start code */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.GetPartInventory());
        productTable.setItems(Inventory.GetProductInventory());

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


    /** Product search functionality */
    public void OnProductsSearch(ActionEvent actionEvent) {
        String searchText = ProductsSearch.getText();
        ObservableList<Product> productsResult = FXCollections.observableArrayList();

        for (Product product : ProductInventory){
            if(String.valueOf(product.getId()).contains(searchText) ||
                    product.getName().contains(searchText)){
                productsResult.add(product);
            }
        }
        productTable.setItems(productsResult);
        ProductsSearch.setText("");
    }

    /** Exit Button */
    public void OnExitButton(ActionEvent actionEvent) {
        System.exit(0);
    }

}