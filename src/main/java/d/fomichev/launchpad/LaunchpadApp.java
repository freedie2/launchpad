package d.fomichev.launchpad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


public class LaunchpadApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {


//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension screenSize = toolkit.getScreenSize();
//
//        int widthScreen = (int) screenSize.getWidth();
//        int heightScreen = (int) screenSize.getHeight();

        FXMLLoader fxmlLoader = new FXMLLoader(LaunchpadApp.class.getResource("form-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Launchpad");
        stage.setScene(scene);
        stage.show();


        FormController formController = fxmlLoader.getController();
        formController.setDbManager(new DBManager());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("assets/style.css")).toExternalForm());
    }

    public static void main(String[] args) {
        launch();
    }
}