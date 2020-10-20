package application.repositories;

import application.database.DBConnector;
import application.entities.StockItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StockItemRepository implements GenericRepository<StockItem> {

    static Connection conn = null;

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

    @Override
    public void insert(StockItem item) {
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

    public static void updateQuantity(StockItem item) {
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

    @Override
    public void update(StockItem item) {
        PreparedStatement statement = null;

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);

            statement = conn.prepareStatement("UPDATE stockitem SET name = ?, quantity = ?, category = ?, size = ? WHERE id = ?");
            statement.setString(1, item.getName());
            statement.setInt(2, item.getQuantity());
            statement.setString(3, item.getCategory().toString());
            statement.setString(4, item.getSize().toString());
            statement.setString(5, item.getId());

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

    @Override
    public void delete(StockItem item) {
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
