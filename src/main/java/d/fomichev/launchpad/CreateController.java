package d.fomichev.launchpad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class CreateController {
    // FXML objects
    @FXML
    public SplitPane root;
    @FXML
    public StackPane content;
    @FXML
    public VBox appWindows;
    @FXML
    public VBox browserWindows;
    @FXML
    public VBox alignment;
    @FXML
    public VBox bindings;
    @FXML
    public VBox additionally;
    @FXML
    public Button appBtn;
    @FXML
    public Button brsBtn;
    @FXML
    public Button algBtn;
    @FXML
    public Button binBtn;
    @FXML
    public Button adtBtn;

    // Other requires
    private DBManager dbManager;
    private Stage primaryStage;

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    int widthScreen = (int) screenSize.getWidth();
    int heightScreen = (int) screenSize.getHeight();


    // Methods
    public void setDbManager(DBManager dbm) {
        dbManager = dbm;
    }

    public void initialize() {
        root.setMaxSize(widthScreen, heightScreen);
        root.setPrefSize(1000, 600);

        appWindows.toFront();

        findApplications();
        fineBrowsers();
    }


    public void openAppWindows(ActionEvent event) {
        algBtn.setStyle("-fx-background-color: none;");
        binBtn.setStyle("-fx-background-color: none;");
        adtBtn.setStyle("-fx-background-color: none;");
        brsBtn.setStyle("-fx-background-color: none;");

        appBtn.setMaxWidth(Double.MAX_VALUE);
        appBtn.setStyle("-fx-background-color: #242424;");
        appWindows.toFront();
    }

    public void openBrowserWindows(ActionEvent event) {
        algBtn.setStyle("-fx-background-color: none;");
        binBtn.setStyle("-fx-background-color: none;");
        adtBtn.setStyle("-fx-background-color: none;");
        appBtn.setStyle("-fx-background-color: none;");

        brsBtn.setMaxWidth(Double.MAX_VALUE);
        brsBtn.setStyle("-fx-background-color: #242424;");
        browserWindows.toFront();
    }

    public void openAlignment(ActionEvent event) {
        appBtn.setStyle("-fx-background-color: none;");
        binBtn.setStyle("-fx-background-color: none;");
        adtBtn.setStyle("-fx-background-color: none;");
        brsBtn.setStyle("-fx-background-color: none;");

        algBtn.setMaxWidth(Double.MAX_VALUE);
        algBtn.setStyle("-fx-background-color: #242424;");
        alignment.toFront();
    }

    public void openBindings(ActionEvent event) {
        algBtn.setStyle("-fx-background-color: none;");
        appBtn.setStyle("-fx-background-color: none;");
        adtBtn.setStyle("-fx-background-color: none;");
        brsBtn.setStyle("-fx-background-color: none;");

        binBtn.setMaxWidth(Double.MAX_VALUE);
        binBtn.setStyle("-fx-background-color: #242424;");
        bindings.toFront();
    }

    public void openAdditionally(ActionEvent event) {
        algBtn.setStyle("-fx-background-color: none;");
        binBtn.setStyle("-fx-background-color: none;");
        appBtn.setStyle("-fx-background-color: none;");
        brsBtn.setStyle("-fx-background-color: none;");

        adtBtn.setMaxWidth(Double.MAX_VALUE);
        adtBtn.setStyle("-fx-background-color: #242424;");
        additionally.toFront();
    }

    public void saveAndCloseConfig(ActionEvent event) {
        try {
            // FIXME: Check requires
            saveCreateDialog();
            // FIXME: Second check saveCreateDialog()
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void saveCreateDialog() {
        // FIXME: Save config in db-configurations under current user and in .config/.properties/another file

    }

    public void findApplications() {
        // FIXME: JNA Windows (Search by HKEY_LOCAL_MACHINE)
    }

    public void fineBrowsers() {
        // FIXME: JNA Windows with find by name of common browsers
    }
}
