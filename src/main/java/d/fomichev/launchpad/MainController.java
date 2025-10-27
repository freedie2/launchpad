package d.fomichev.launchpad;

import javafx.scene.layout.VBox;

import java.awt.*;

public class MainController {
    public VBox root;
    // FXML objects


    // other requires
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    int widthScreen = (int) screenSize.getWidth();
    int heightScreen = (int) screenSize.getHeight();

    // Methods

    public void initialize() {
        root.setMaxSize(widthScreen, heightScreen);
        root.setPrefSize(1000, 600);

    }
}
