package d.fomichev.launchpad;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

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

    }

    public void openMainWindow(ActionEvent event) throws Exception {
        // Загружаем главное окно
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        MainController mainController = loader.getController();
        mainController.setDbManager(new DBManager());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("assets/style.css")).toExternalForm());

        Stage mainStage = new Stage();
        mainStage.setTitle("Главная");
        mainStage.setScene(scene);
        mainStage.show();

        // Закрываем первое окно
        if (primaryStage != null) {
            primaryStage.close();
        }
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
                openMainWindow(actionEvent);
            } else {
                // Пользователь не найден
                log_error_message.setVisible(true);
            }
        } catch (Exception e) {
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

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

}