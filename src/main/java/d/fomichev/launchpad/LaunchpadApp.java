package d.fomichev.launchpad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;


public class LaunchpadApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {


//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension screenSize = toolkit.getScreenSize();
//
//        int widthScreen = (int) screenSize.getWidth();
//        int heightScreen = (int) screenSize.getHeight();

        FXMLLoader fxmlLoader = new FXMLLoader(LaunchpadApp.class.getResource("main-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Launchpad");
        stage.setScene(scene);
        stage.show();

        MainController mainController = fxmlLoader.getController();
        mainController.setDbManager(new DBManager());
    }

    public static void main(String[] args) {
        launch();
    }
}