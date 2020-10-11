package application.repositories;

import application.database.DBConnector;
import application.entities.StockItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockItemRepository implements GenericRepository<StockItem> {

    static Connection conn = null;

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
