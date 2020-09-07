package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:file:C:\\Users\\caixa\\h2", "sa", "");
        return conn;
    }

}
