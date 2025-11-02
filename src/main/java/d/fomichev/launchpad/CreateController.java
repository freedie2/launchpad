package d.fomichev.launchpad;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

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
    @FXML
    public GridPane gridApps;
    @FXML
    public VBox vboxFindPath;

    // Other requires
    private DBManager dbManager;
    private Stage primaryStage;

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    int widthScreen = (int) screenSize.getWidth();
    int heightScreen = (int) screenSize.getHeight();

    public enum Category {
        BROWSER, OFFICE, MEDIA, GAME, DEV, SYSTEM, DEFAULT
    }



    // Methods
    public void setDbManager(DBManager dbm) {
        dbManager = dbm;
    }

    public void initialize() {
        root.setMaxSize(widthScreen, heightScreen);
        root.setPrefSize(1000, 600);

        appWindows.toFront();

        // Загрузка найденных приложений в GridPane
        List<AppEntity> apps = findApplicationsNames();
        int max = Math.min(apps.size(), 25);

        for (int i = 0; i < max; i++) {
            AppEntity app = apps.get(i);

            // Определяем категорию
            Category category = detectCategory(app.getName());

            // Загружаем иконку
            Image icon = CategoryIconLoader.loadIcon(category);

            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(48);
            imageView.setFitHeight(48);
            imageView.setPreserveRatio(true);

            Label label = new Label(app.getName());
            label.setWrapText(true);
            label.setMaxWidth(80);
            label.setAlignment(Pos.CENTER);

            VBox container = new VBox(5, imageView, label);
            container.setAlignment(Pos.CENTER);

            GridPane.setRowIndex(container, i / 5);
            GridPane.setColumnIndex(container, i % 5);
            gridApps.getChildren().add(container);
        }

        // Загрузка найденных браузеров в GridPane
        findBrowsers();
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

    public List<AppEntity> findApplicationsNames() {
        List<AppEntity> apps = new ArrayList<>();

        String[] rootKeys = {
                "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall",
                "SOFTWARE\\WOW6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall"
        };

        for (String rootKey : rootKeys) {
            try {
                String[] subkeys = Advapi32Util.registryGetKeys(WinReg.HKEY_LOCAL_MACHINE, rootKey);
                for (String subkey : subkeys) {
                    String keyPath = rootKey + "\\" + subkey;
                    try {
                        Map<String, Object> values = Advapi32Util.registryGetValues(WinReg.HKEY_LOCAL_MACHINE, keyPath);
                        String displayName = (String) values.get("DisplayName");
                        String displayIcon = (String) values.get("DisplayIcon");
                        if (displayName != null && !displayName.trim().isEmpty()) {
                            apps.add(new AppEntity(displayName, displayIcon));
                        }
                    } catch (Exception ignored) { }
                }
            } catch (Exception e) {
                System.out.println("Нет доступа или ключ отсутствует");
            }
        }

        return apps;
    }

    public Category detectCategory(String appName) {
        if (appName == null) return Category.DEFAULT;

        String name = appName.toLowerCase();

        // Браузеры
        if (name.contains("chrome") || name.contains("firefox") || name.contains("edge")
                || name.contains("opera") || name.contains("brave") || name.contains("yandex")) {
            return Category.BROWSER;
        }

        // Офис
        if (name.contains("word") || name.contains("excel") || name.contains("powerpoint")
                || name.contains("office") || name.contains("libreoffice") || name.contains("openoffice")) {
            return Category.OFFICE;
        }

        // Медиа
        if (name.contains("vlc") || name.contains("spotify") || name.contains("youtube")
                || name.contains("media player") || name.contains("kodi") || name.contains("mpc-hc")) {
            return Category.MEDIA;
        }

        // Игры
        if (name.contains("steam") || name.contains("epic") || name.contains("origin")
                || name.contains("minecraft") || name.contains("game") || name.contains("launcher")) {
            return Category.GAME;
        }

        // Разработка
        if (name.contains("intellij") || name.contains("eclipse") || name.contains("visual studio")
                || name.contains("code") || name.contains("netbeans") || name.contains("android studio")
                || name.contains("git") || name.contains("jdk") || name.contains("python")) {
            return Category.DEV;
        }

        // Системные
        if (name.contains("windows") || name.contains("update") || name.contains("security")
                || name.contains("defender") || name.contains("runtime") || name.contains("driver")
                || name.contains("microsoft") || name.contains(".net")) {
            return Category.SYSTEM;
        }

        return Category.DEFAULT;
    }


    public void findBrowsers() {
        // FIXME: JNA Windows with find by name of common browsers
    }

    public void buttonSelect(ActionEvent event) {
    }

    public void buttonNext(ActionEvent event) {
    }
}
