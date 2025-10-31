package d.fomichev.launchpad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Objects;

public class MainController {

    // FXML objects
    @FXML
    public VBox root;

    @FXML
    public GridPane gridPane;

    // other requires
    private DBManager dbManager;
    private Stage primaryStage;


    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    int widthScreen = (int) screenSize.getWidth();
    int heightScreen = (int) screenSize.getHeight();




    // Methods

    public void initialize() {
        root.setMaxSize(widthScreen, heightScreen);
        root.setPrefSize(1000, 600);


        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 4; col++) {
                Pane cell = new Pane();
                cell.setStyle("-fx-background-color: #2a2d30; -fx-border-color: #444; -fx-border-width: 1;");
                GridPane.setHgrow(cell, Priority.ALWAYS);
                GridPane.setVgrow(cell, Priority.ALWAYS);
                gridPane.add(cell, col, row);
            }
        }
    }

    public void setDbManager(DBManager dbm) {
        dbManager = dbm;
    }




    public void createConfig(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        CreateController createController = loader.getController();
        createController.setDbManager(new DBManager());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("assets/style.css")).toExternalForm());

        Stage createStage = new Stage();
        createStage.setTitle("Новая конфигурация");
        createStage.setScene(scene);
        createStage.show();
    }

    public void openSettings(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        SettingsController settingsController = loader.getController();
        settingsController.setDbManager(new DBManager());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("assets/style.css")).toExternalForm());

        Stage settingsStage = new Stage();
        settingsStage.setTitle("Настройки");
        settingsStage.setScene(scene);
        settingsStage.show();

    }


}
