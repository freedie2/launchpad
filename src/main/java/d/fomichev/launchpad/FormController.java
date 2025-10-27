package d.fomichev.launchpad;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;

import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class FormController {
    // FXML objects

    @FXML
    public Button button_reg;

    @FXML
    public Label login_label;

    @FXML
    private SplitPane root;

    @FXML
    public Label welcome_label;

    @FXML
    public Button button_start;

    @FXML
    public TextField usernameField;

    @FXML
    public TextField passwordField;

    @FXML
    public Label log_error_message;


    // another requires
    private DBManager dbManager;

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    int widthScreen = (int) screenSize.getWidth();
    int heightScreen = (int) screenSize.getHeight();



    // Methods
    public void initialize() {
        root.setMaxSize(widthScreen, heightScreen);
        root.setPrefSize(1000, 600);

    }

    public void fadeOut(Node node) {
        FadeTransition fadeOutLabel = new FadeTransition(Duration.seconds(1), node);
        fadeOutLabel.setToValue(0.0);
        fadeOutLabel.setOnFinished(_ -> {
            node.setVisible(false);
        });
        fadeOutLabel.play();
    }

    public void closeWelcomeWindow() {
        fadeOut(root);
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

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            log_error_message.setVisible(true);
            return;
        }

        try {
            UserConfig config = dbManager.checkUserConf(username, password);
            if (config != null) {
                System.out.println("Вход выполнен: " + config.getUsername());
                closeWelcomeWindow();
//                openMainWindow();
            } else {
                // Пользователь не найден
                log_error_message.setVisible(true);
            }
        } catch (RuntimeException e) {
            // Ошибка подключения к БД и т.д.

            e.printStackTrace();
        }
    }

    public void setDbManager(DBManager dbm) {
        dbManager = dbm;
    }

    public void regUser(ActionEvent actionEvent) {
        login_label.setText("Log Up");
        button_start.setVisible(false);
        button_reg.setText("Нажмите ещё раз, чтобы зарегистрироваться");


        UserConfig userConfig = new UserConfig(usernameField.getText(), passwordField.getText(), "dark", "ru");


        try {
            log_error_message.setVisible(false);
            if (userConfig.getUsername().isEmpty() || userConfig.getPassword().isEmpty()) {
                log_error_message.setText("Не оставляйте поля пустыми. Им одиноко");
                log_error_message.setVisible(true);
                return;
            }

            UserConfig configCheck = dbManager.checkUserConf(userConfig.getUsername(), userConfig.getPassword());

            if (configCheck != null) {
                log_error_message.setText("Пользователь уже существует");
                log_error_message.setVisible(true);
                System.out.println("Пользователь " + configCheck.getUsername() + "уже существует.");
            } else {
                dbManager.createNewUser(userConfig);
                System.out.println("Пользователь создан: " + userConfig.getUsername());

                login_label.setText("Log In");
                button_start.setVisible(true);
                button_reg.setText("Зарегистрироваться");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}