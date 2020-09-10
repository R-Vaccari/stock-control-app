package application.gui;

import application.database.DBConnector;
import application.database.SQL;
import application.entities.StockItem;
import application.entities.enums.Category;
import application.entities.enums.Size;
import application.exceptions.MissingSelectionException;
import application.gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Button addEntryBt;
    @FXML
    private Button increaseQuantityBt;
    @FXML
    private Button decreaseQuantityBt;
    @FXML
    private Button refreshBt;
    @FXML
    private Button deleteBt;
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
    @FXML
    private MenuItem aboutBt;

    private ObservableList<StockItem> masterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = null;

        try {
            conn = DBConnector.getConnection();
            DatabaseMetaData dbm = conn.getMetaData();

            ResultSet tables = dbm.getTables(null, null, "STOCKITEM", null);
            if (!tables.next()) SQL.createItemTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        List<StockItem> items = SQL.buildListFromDB();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_size.setCellValueFactory(new PropertyValueFactory<>("size"));

        table.getItems().addAll(items);
        masterData.addAll(items);

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
    }

    @FXML
    public void onAboutBt() {
        try {
            URL url = new File("src\\application\\gui\\AboutView.fxml").toURI().toURL();
            AnchorPane aboutPane = FXMLLoader.load(url);

            mainPane.setCenter(aboutPane);

            //Scene mainScene = Main.getMainScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAddEntryBt() throws IOException {
        URL url = new File("src\\application\\gui\\EntryView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Stock Control");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void onIncreaseQuantityBt() {
        try {
            StockItem item = table.getSelectionModel().getSelectedItem();
            if (item == null) throw new MissingSelectionException();

            item.setQuantity(item.getQuantity() + 1);

            SQL.updateQuantitySQL(item);
            table.refresh();
        } catch (MissingSelectionException e) {
            Alerts.showAlert("Missing Selected Item", null, "One item from table must be selected.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onDecreaseQuantityBt() {
        try {
            StockItem item = table.getSelectionModel().getSelectedItem();
            if (item == null) throw new MissingSelectionException();

            item.setQuantity(item.getQuantity() - 1);

            if (item.getQuantity() >= 0) SQL.updateQuantitySQL(item);
            else {
                Alerts.showAlert("Negative Quantity", null, "Item quantity may not be negative.",
                        Alert.AlertType.ERROR);
                item.setQuantity(0);
            }
            table.refresh();
        } catch (MissingSelectionException e) {
            Alerts.showAlert("Missing Selected Item", null, "One item from table must be selected.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onDeleteBt() {
        try {
            StockItem item = table.getSelectionModel().getSelectedItem();
            if (item == null) throw new MissingSelectionException();

            SQL.deleteSQL(item);
            List<StockItem> items = SQL.buildListFromDB();

            masterData.removeAll(masterData);
            masterData.addAll(items);
        } catch (MissingSelectionException e) {
            Alerts.showAlert("Missing Selected Item", null, "One item from table must be selected.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onRefreshBt() {
        List<StockItem> items = SQL.buildListFromDB();
        masterData.removeAll(masterData);
        masterData.addAll(items);
    }
}
