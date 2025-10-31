package d.fomichev.launchpad;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
import java.util.Properties;

public class FormController {
    // FXML objects

    @FXML
    public Button button_reg;

    @FXML
    public Label login_label;

    @FXML
    public CheckBox rememberMe;

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
    private static final String CONFIG_FILE = "db.config";


    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    int widthScreen = (int) screenSize.getWidth();
    int heightScreen = (int) screenSize.getHeight();


    // Methods
    public void initialize() {
        root.setMaxSize(widthScreen, heightScreen);
        root.setPrefSize(1000, 600);

        loadConfig();

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

        Properties config = saveConfig();

        if (username.isEmpty() || password.isEmpty()) {
            log_error_message.setVisible(true);
            return;
        }

        try {
            UserEntity entity = dbManager.checkUserConf(username, password);
            if (entity != null) {
                System.out.println("Вход выполнен: " + entity.getUsername());
                config.getProperty("user.name");
                config.getProperty("user.password");
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


        UserEntity userEntity = new UserEntity(usernameField.getText(), passwordField.getText(), "dark", "ru");


        try {
            log_error_message.setVisible(false);
            if (userEntity.getUsername().isEmpty() || userEntity.getPassword().isEmpty()) {
                log_error_message.setText("Не оставляйте поля пустыми. Им одиноко");
                log_error_message.setVisible(true);
                return;
            }

            UserEntity configCheck = dbManager.checkUserConf(userEntity.getUsername(), userEntity.getPassword());

            if (configCheck != null) {
                log_error_message.setText("Пользователь уже существует");
                log_error_message.setVisible(true);
                System.out.println("Пользователь " + configCheck.getUsername() + "уже существует.");
            } else {
                dbManager.createNewUser(userEntity);
                System.out.println("Пользователь создан: " + userEntity.getUsername());

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


    private Properties saveConfig() {
        Properties props = new Properties();

        String username = usernameField.getText().trim();
        String userpass = passwordField.getText().trim();

        props.setProperty("user.name", username);
        props.setProperty("user.password", userpass);


        try (OutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Конфигурация БД сохранена " + java.time.LocalDateTime.now());
            System.out.println("Конфигурация сохранена в " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Не удалось сохранить конфиг: " + e.getMessage());
        }

        return props;
    }

    //    Функция автоматической выгрузки данных из конфига в форму, при условии наличия самого конфига
    public void loadConfig() {
        try (InputStream in = new FileInputStream(CONFIG_FILE)) {
            Properties props = new Properties();
            props.load(in);

            usernameField.setText(props.getProperty("user.name", ""));
            passwordField.setText(props.getProperty("user.password", ""));

            System.out.println("Конфиг загружен в форму.");
        } catch (IOException e) {
            System.err.println("Не удалось загрузить конфиг: " + e.getMessage());
        }
    }

}