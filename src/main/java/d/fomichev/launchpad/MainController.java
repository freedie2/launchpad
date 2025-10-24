package d.fomichev.launchpad;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainController {
    private double width, height;

    public MainController(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @FXML
    private VBox root;

    public void initialize() {
        root.setPrefSize(width, height);
    }
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
}