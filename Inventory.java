package school.schoolproject.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class that contains product and part data and functions to act on that data */
public class Inventory {
    public static ObservableList<Product> ProductInventory = FXCollections.observableArrayList();
    public static ObservableList<Part> PartInventory = FXCollections.observableArrayList();

    /** Add part functionality */
    public static void AddPart(Part part){
        PartInventory.add(part);
    }
    /** Remove part functionality */
    public static void RemovePart(Part part){
        PartInventory.remove(part);
    }
    /** Returns all the parts in the inventory */
    public static ObservableList<Part> GetPartInventory(){
        return PartInventory;
    }
    /** Looks up parts by ID */
    public static Part LookupPart(int PartID){
        Part found = null;
        for(Part part : PartInventory){
            if(part.getId() == PartID){
                found = part;
            }
        }
        return found;
    }
    /** Looks up parts by name */
    public static Part LookupPart(String PartName){
        Part found = null;
        for(Part part : PartInventory){
            if(part.getName() == PartName){
                found = part;
            }
        }
        return found;
    }

    /** Updates part data*/
    public static void UpdatePart(int Index, Part SelectedPart){
        PartInventory.set(Index, SelectedPart);
    }

    /** Add product functionality */
    public static void AddProduct(Product NewPro){
        ProductInventory.add(NewPro);
    }
    /** Remove product functionality */
    public static void RemoveProduct(Product product){
        ProductInventory.remove(product);
    }
    /** Returns all products in inventory */
    public static ObservableList<Product> GetProductInventory(){
        return ProductInventory;
    }
    /** Looks up products by id */
    public static Product LookupProduct(int ProductID){
        Product found = null;
        for(Product product : ProductInventory){
            if(product.getId() == ProductID){
                found = product;
            }
        }
        return found;
    }
    /** Looks up products by name */
    public static Product LookupProduct(String ProductName){
        Product found = null;
        for(Product product : ProductInventory){
            if(product.getName() == ProductName){
                found = product;
            }
        }
        return found;
    }

    /** Updates product data */
    public static void UpdateProduct(int Index, Product SelectedProduct){
        ProductInventory.set(Index, SelectedProduct);
    }

    /** Test Data */
    static {
        addTestData();
    }
    private static void addTestData(){
        Part Brakes = new InHouse(1,"Brakes",15.00, 50, 5, 100, 1);
        Inventory.AddPart(Brakes);
        Part Wheel = new InHouse(2, "Wheel", 11.00,50, 5, 100, 2);
        Inventory.AddPart(Wheel);
        Part Seat = new Outsourced(3, "Seat", 15.00,50, 5, 100, "Ben's Bikes");
        Inventory.AddPart(Seat);
        Part Spoke = new Outsourced(4, "Spoke", 11.00,50, 5, 100, "Bill's Bikes");
        Inventory.AddPart(Spoke);

        Product GiantBike = new Product(1000,"Giant Bike", 299.99, 50, 5,100);
        Inventory.AddProduct(GiantBike);
        Product Tricycle = new Product(1001,"Tricycle", 99.99, 50, 5,100);
        Inventory.AddProduct(Tricycle);
    }
}
