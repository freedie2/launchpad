package d.fomichev.launchpad;

import java.sql.*;

public class DBManager {


    private Connection connection;

    public DBManager() throws Exception {
        connect();
    }

    public void connect() throws Exception {
        if (connection != null) {
            System.out.println("Подключение уже выполнено");
            return;
        }

        try {
            String user = "postgres";
            String password = "2702";
            String url = "jdbc:postgresql://localhost:5432/db";

            connection = DriverManager.getConnection(url, user, password);

            System.out.printf("Подключение установлено (%s, пользователь %s)\n", url, user);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении: " + e.getMessage());
            throw new Exception(e);
        }
    }

    public void disconnect() throws Exception {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Соединение с БД успешно закрыто");
            } catch (Exception e) {
                System.out.println("Ошибка при закрытии соединения с БД: " + e.getMessage());
                throw new Exception(e);
            }

        } else {
            System.out.println("Подключение не установлено");
        }
    }

    public UserEntity checkUserConf(String username, String password) {
        String selectSql = """
                SELECT u.username, u.password, c.theme, c.language
                FROM users u
                JOIN configurations c ON u.id = c.user_id
                WHERE u.username = ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(selectSql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    if (password.equals(storedPassword)) {
                        return new UserEntity(
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("theme"),
                                rs.getString("language")
                        );
                    } else {
                        return null;
                    }


                } else {
                    System.out.println("Пользователь '" + username + "' не найден или у него нет конфигурации.");
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке пользователя: " + e.getMessage(), e);
        }

    }

    public void createNewUser(UserEntity userEntity) throws Exception {
        String insertSql = String.format("""
                WITH new_user AS (
                    INSERT INTO users (username, password)
                    VALUES ('%s', '%s')
                    RETURNING id
                )
                INSERT INTO configurations (user_id, theme, language)
                SELECT id, 'dark', 'ru'
                FROM new_user;
                """, userEntity.getUsername(), userEntity.getPassword());

        try (var st = connection.prepareStatement(insertSql)){
            int cnt = st.executeUpdate();
            System.out.println("Добавлен пользовтель: " + cnt);
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении: " + e.getMessage());
            throw new Exception(e);
        }

    }


}
