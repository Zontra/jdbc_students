package fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created: 20.12.2022
 *
 * @author Patrick Schneeweis (patrick)
 */
public class Sample extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlPath = "../Pathfinder.fxml";

        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource(fxmlPath));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pathfinder");
        stage.setResizable(false);
        stage.show();
    }
}
