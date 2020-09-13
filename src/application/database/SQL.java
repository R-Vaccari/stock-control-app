package application.database;

import application.entities.StockItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.*;
import java.util.List;

public class SQL {

    static Connection conn = null;

    public static void checkForTable() {
        ResultSet tables = null;
        try {
            conn = DBConnector.getConnection();
            DatabaseMetaData dbm = conn.getMetaData();

            tables = dbm.getTables(null, null, "STOCKITEM", null);
            if (!tables.next()) SQL.createItemTable();
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

            statement = conn.prepareStatement("CREATE TABLE stockitem (id varchar(10), name varchar(20) PRIMARY KEY, quantity int CHECK (quantity >= 0), " +
                    "category varchar(10), size varchar(10))");

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

    public static List<StockItem> buildListFromDB() {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<StockItem>> handler = new BeanListHandler<StockItem>(StockItem.class);

        try {
            conn = DBConnector.getConnection();
            List<StockItem> items = qr.query(conn, "SELECT * FROM stockitem", handler);

            return items;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void executeSQL(StockItem item) {
        PreparedStatement statement = null;

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);

            statement = conn.prepareStatement("INSERT INTO stockitem VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, item.getId());
            statement.setString(2, item.getName());
            statement.setInt(3, item.getQuantity());
            statement.setString(4, item.getCategory().toString());
            statement.setString(5, item.getSize().toString());

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

    public static void updateQuantitySQL(StockItem item) {
        PreparedStatement statement = null;

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);

            statement = conn.prepareStatement("UPDATE stockitem SET quantity = ? WHERE id = ?");
            statement.setInt(1, item.getQuantity());
            statement.setString(2, item.getId());

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

    public static void deleteSQL(StockItem item) {
        PreparedStatement statement = null;

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);

            statement = conn.prepareStatement("DELETE FROM stockitem WHERE id = ?");
            statement.setString(1, item.getId());

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
