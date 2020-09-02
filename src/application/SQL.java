package application;

import application.entities.StockItem;
import application.entities.enums.Category;
import application.entities.enums.Size;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SQL {

    public static void createItemTable() throws SQLException {
        Connection conn = DBConnector.getConnection();

        PreparedStatement statement = conn.prepareStatement("CREATE TABLE stockitem (id varchar(10), name varchar(20), quantity int, " +
                "category varchar(10), size varchar(10))");
        statement.executeUpdate();
        statement.close();

        conn.close();
    }

    public static void insertTestItems() throws SQLException {
        Connection conn = DBConnector.getConnection();

        PreparedStatement statement = conn.prepareStatement("INSERT INTO stockitem VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, "121212");
        statement.setString(2, "TEST");
        statement.setInt(3, 1);
        statement.setString(4, Category.LUGAGGE.toString());
        statement.setString(5, Size.BIG.toString());
        statement.executeUpdate();
        statement.close();

        conn.close();
    }

    public static List<StockItem> buildListFromDB() throws SQLException {
        Connection conn = DBConnector.getConnection();

        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<StockItem>> handler = new BeanListHandler<StockItem>(StockItem.class);

        List<StockItem> items = qr.query(conn, "SELECT * FROM stockitem", handler);
        conn.close();

        return items;
    }

    public static void executeSQL(StockItem item, Button registerBt) throws SQLException {
        Connection conn = DBConnector.getConnection();

        PreparedStatement statement = conn.prepareStatement("INSERT INTO stockitem VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, item.getId());
        statement.setString(2, item.getId());
        statement.setInt(3, item.getQuantity());
        statement.setString(4, item.getCategory().toString());
        statement.setString(5, item.getSize().toString());
        statement.executeUpdate();
        statement.close();

        conn.close();
        Stage stage = (Stage) registerBt.getScene().getWindow();
        stage.close();
    }

    public static void updateQuantitySQL(StockItem item) throws SQLException {
        Connection conn = DBConnector.getConnection();

        PreparedStatement statement = conn.prepareStatement("UPDATE stockitem SET quantity = " + item.getQuantity() + " WHERE id = " + item.getId());
        statement.executeUpdate();
        statement.close();

        conn.close();
    }
}
