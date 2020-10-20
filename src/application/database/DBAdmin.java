package application.database;

import application.entities.StockItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.*;
import java.util.List;

public class DBAdmin {

    static Connection conn = null;

    public static void checkForTable() {
        ResultSet tables = null;
        try {
            conn = DBConnector.getConnection();
            DatabaseMetaData dbm = conn.getMetaData();

            tables = dbm.getTables(null, null, "STOCKITEM", null);
            if (!tables.next()) DBAdmin.createItemTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
                if (tables != null) tables.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createItemTable()  {
        PreparedStatement statement = null;

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);

            statement = conn.prepareStatement("CREATE TABLE stockitem (id varchar(15) PRIMARY KEY, name varchar(30), quantity int CHECK (quantity >= 0), " +
                    "category varchar(15), size varchar(15))");

            statement.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




}
