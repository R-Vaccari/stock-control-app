package application;

import com.sun.jdi.connect.Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:h2:~\\h2\\stockdb", "sa", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }
}
