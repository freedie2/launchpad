package d.fomichev.launchpad;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.InputStream;

import static jdk.xml.internal.SecuritySupport.getResourceAsStream;

public class CategoryIconLoader {
    private static final String ICON_PATH_PREFIX = "assets/";

    public static Image loadIcon(CreateController.Category category) {
        String fileName;
        switch (category) {
            case BROWSER -> fileName = "browser.png";
            case OFFICE -> fileName = "office.png";
            case MEDIA -> fileName = "media.png";
            case GAME -> fileName = "game.png";
            case DEV -> fileName = "dev.png";
            case SYSTEM -> fileName = "system.png";
            default -> fileName = "default.png";
        }

        // Загружаем из ресурсов
        String path = ICON_PATH_PREFIX + fileName;
        InputStream is = CategoryIconLoader.class.getResourceAsStream(path);
        if (is == null) {
            System.err.println("Иконка не найдена: " + path + ". Используем default.png");
            is = CategoryIconLoader.class.getResourceAsStream(ICON_PATH_PREFIX + "default.png");
        }

        // Если и default не найден — создаём пустое изображение
        if (is == null) {
            return new WritableImage(48, 48); // пустое изображение
        }

        return new Image(is);
    }
}
