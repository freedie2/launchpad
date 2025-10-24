package d.fomichev.launchpad;


import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.Objects;

public class SettingsController {

    @FXML
    private ChoiceBox<String> choiceBox;

    public void checkChoiceBox() {
        try {
            if (Objects.equals(choiceBox.getValue(), "1920x1080")){

            } else if (Objects.equals(choiceBox.getValue(), "1536x864")) {

            } else if (Objects.equals(choiceBox.getValue(), "1366x768")) {

            } else if (Objects.equals(choiceBox.getValue(), "1280x720")) {

            } else if (Objects.equals(choiceBox.getValue(), "1440×900")) {

            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        choiceBox.getItems().addAll("1920x1080", "1536x864", "1366x768", "1280x720", "1440×900");
    }
}