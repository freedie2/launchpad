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
}
