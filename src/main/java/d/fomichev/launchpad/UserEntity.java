package d.fomichev.launchpad;

// Простой класс для хранения данных пользователя и его конфигурации
public class UserEntity {
    private final String username;
    private final String password;
    private final String theme;
    private final String language;

    public UserEntity(String username, String password, String theme, String language) {
        this.username = username;
        this.password = password;
        this.theme = theme;
        this.language = language;
    }

    // Геттеры
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTheme() {
        return theme;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "UserConfig{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", theme='" + theme + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}