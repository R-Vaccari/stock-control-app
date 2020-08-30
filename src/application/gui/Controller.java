package application.gui;

import application.DBConnector;
import application.entities.StockItem;
import application.entities.enums.Category;
import application.entities.enums.Size;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.naming.NamingException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private Button addEntryBt;
    @FXML
    private TableView<StockItem> table = new TableView<>();
    @FXML
    private TableColumn<StockItem, String> col_id;
    @FXML
    private TableColumn<StockItem, String> col_name;
    @FXML
    private TableColumn<StockItem, Integer> col_quantity;
    @FXML
    private TableColumn<StockItem, Category> col_category;
    @FXML
    private TableColumn<StockItem, Size> col_size;

    @FXML
    public void onAddEntryBt() throws IOException {
        URL url = new File("src\\application\\gui\\EntryView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Stock Control");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

         Connection conn = DBConnector.getConnection();

        try {
            PreparedStatement statementTable = conn.prepareStatement("CREATE TABLE stockitem (id varchar(10), name varchar(20), quantity int, " +
                    "category varchar(10), size varchar(10))");
            statementTable.executeUpdate();
            statementTable.close();

            PreparedStatement statementInsert = conn.prepareStatement("INSERT INTO stockitem VALUES (?, ?, ?, ?, ?)");
            statementInsert.setString(1, "121212");
            statementInsert.setString(2, "TEST");
            statementInsert.setInt(3, 1);
            statementInsert.setString(4, Category.LUGAGGE.toString());
            statementInsert.setString(5, Size.BIG.toString());
            statementInsert.executeUpdate();
            statementInsert.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<List<StockItem>> handler = new BeanListHandler<StockItem>(StockItem.class);

            List<StockItem> items = qr.query(conn, "SELECT * FROM stockitem", handler);

            items.add(new StockItem("090909", "TEST2", 2, Category.BACKPACK, Size.SMALL));

            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
            col_size.setCellValueFactory(new PropertyValueFactory<>("size"));

            table.getItems().addAll(items);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        //table.setItems(items);
        //table.getColumns().addAll(col_id, col_name, col_quantity, col_category, col_size);
    }
}
