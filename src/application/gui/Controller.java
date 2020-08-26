package application.gui;

import application.DBConnector;
import application.entities.StockItem;
import application.entities.enums.Category;
import application.entities.enums.Size;
import javafx.collections.FXCollections;
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


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button addEntryBt;
    @FXML
    private TableView<StockItem> table;
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

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
    }

    public ObservableList<StockItem> getStockItems() {
        ObservableList<StockItem> items = FXCollections.observableArrayList();
        return items;
    }
}
