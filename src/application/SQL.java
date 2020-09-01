package application;

import application.entities.StockItem;
import application.entities.enums.Category;
import application.entities.enums.Size;
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

        PreparedStatement statementTable = conn.prepareStatement("CREATE TABLE stockitem (id varchar(10), name varchar(20), quantity int, " +
                "category varchar(10), size varchar(10))");
        statementTable.executeUpdate();
        statementTable.close();

        conn.close();
    }

    public static void insertTestItems() throws SQLException {
        Connection conn = DBConnector.getConnection();

        PreparedStatement statementInsert = conn.prepareStatement("INSERT INTO stockitem VALUES (?, ?, ?, ?, ?)");
        statementInsert.setString(1, "121212");
        statementInsert.setString(2, "TEST");
        statementInsert.setInt(3, 1);
        statementInsert.setString(4, Category.LUGAGGE.toString());
        statementInsert.setString(5, Size.BIG.toString());
        statementInsert.executeUpdate();
        statementInsert.close();

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
}
