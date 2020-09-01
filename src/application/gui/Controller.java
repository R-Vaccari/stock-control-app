package application.gui;

import application.DBConnector;
import application.SQL;
import application.entities.StockItem;
import application.entities.enums.Category;
import application.entities.enums.Size;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button addEntryBt;
    @FXML
    private TextField fieldFilter;
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

    private ObservableList<StockItem> masterData = FXCollections.observableArrayList();

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
            SQL.createItemTable();
            SQL.insertTestItems();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            List<StockItem> items = SQL.buildListFromDB();
            items.add(new StockItem("090909", "TEST2", 2, Category.BACKPACK, Size.SMALL));

            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
            col_size.setCellValueFactory(new PropertyValueFactory<>("size"));

            table.getItems().addAll(items);
            masterData.addAll(items);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        FilteredList<StockItem> filteredData = new FilteredList<>(masterData, p -> true);  // allows for filtering. p -> true is a standard expression

        fieldFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(stockItem -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (stockItem.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (stockItem.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<StockItem> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);

        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
