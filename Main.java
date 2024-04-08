package school.schoolproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**Javadoc is included in SchoolProject.zip*/
/**Creates inventory management application*/

public class Main extends Application {

    /**loads main.fxml*/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /**Launches the application*/
    public static void main(String[] args) {
        launch();
    }
}