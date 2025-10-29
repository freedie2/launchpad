package d.fomichev.launchpad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.prefs.Preferences;


public class LaunchpadApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("form-view.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("assets/style.css")).toExternalForm());

        FormController controller = loader.getController();
        controller.setPrimaryStage(stage);

        DBManager dbm = new DBManager();
        controller.setDbManager(dbm);

        stage.setTitle("Launchpad");
        stage.setScene(scene);
        stage.show();


//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension screenSize = toolkit.getScreenSize();
//
//        int widthScreen = (int) screenSize.getWidth();
//        int heightScreen = (int) screenSize.getHeight();
//
//        FXMLLoader fxmlLoader = new FXMLLoader(LaunchpadApp.class.getResource("form-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
//
//        FormController formController = fxmlLoader.getController();
//        formController.setPrimaryStage(stage);
//        formController.setPrimaryPrefs(prefs);
//
//        stage.setTitle("Launchpad");
//        stage.setScene(scene);
//        stage.show();
//
//
//        try {
//            DBManager dbm = new DBManager();
//            formController.setDbManager(dbm);
//
//            stage.setTitle("Launchpad");
//
//            stage.setScene(scene);
//
//            // Start - Open the Main
//            FXMLLoader fxmlLoaderMain = new FXMLLoader(getClass().getResource("main-view.fxml"));
//            AnchorPane mainContent = fxmlLoaderMain.load();
//            MainController mainController = fxmlLoaderMain.getController();
//            mainController.setDbManager(dbm);
//            Dialog<Void> mainDialog = new Dialog<>();
//            mainDialog.setTitle("Главная");
//            mainDialog.getDialogPane().setContent(mainContent);
//            Stage mainDialogStage = (Stage) mainDialog.getDialogPane().getScene().getWindow();
//            mainDialogStage.setOnCloseRequest(evt->{
//                System.out.println("Закрытие модального диалога!");
//                mainDialog.close();
//            });
//            formController.setMainDialogStage(mainDialogStage);
//            // End - Open the Main
//
//
//            stage.show();
//        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Ошибка");
//            alert.setHeaderText("Что-то пошло не так:");
//            alert.setContentText(e.getMessage());
//            alert.initModality(Modality.APPLICATION_MODAL);
//            alert.showAndWait();
//
////            formDialogStage.showAndWait();
//
//            throw new RuntimeException(e);
//        }


//        FormController formController = fxmlLoader.getController();
//        formController.setDbManager(new DBManager());

//        mainController.setDbManager(new DBManager());

//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("assets/style.css")).toExternalForm());
    }

    public static void main(String[] args) {
        launch();
    }
}