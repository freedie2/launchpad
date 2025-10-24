package d.fomichev.launchpad;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainController {

    // another requires
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    int widthScreen = (int) screenSize.getWidth();
    int heightScreen = (int) screenSize.getHeight();

    // FXML objects
    @FXML
    private VBox root;

    @FXML
    public Label welcome_label;

    @FXML
    public Button button_start;

    // Methods
    public void initialize() {
        root.setMaxSize(widthScreen, heightScreen);
        root.setPrefSize(1000, 600);

    }

    public void closeWelcomeWindow() {
        FadeTransition fadeOutLabel = new FadeTransition(Duration.seconds(1), welcome_label);
        fadeOutLabel.setToValue(0.0);
        fadeOutLabel.setOnFinished(e -> {
            welcome_label.setVisible(false);
        });
        fadeOutLabel.play();

        FadeTransition fadeOutBtn = new FadeTransition(Duration.seconds(1), button_start);
        fadeOutBtn.setToValue(0.0);
        fadeOutBtn.setOnFinished(e -> {
            button_start.setVisible(false);
        });
        fadeOutBtn.play();

    }

    public void openMainWindow() {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), welcome_label);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                }
            }, 1200);
    }

    @FXML
    public void startWorking(ActionEvent actionEvent) {
        closeWelcomeWindow();
        openMainWindow();

    }
}