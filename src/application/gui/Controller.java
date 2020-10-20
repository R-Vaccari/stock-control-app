package application.gui;

import application.repositories.RepositoryAdmin;
import application.repositories.StockItemRepository;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private BorderPane mainPane;
    @FXML private AnchorPane centerPane;
    @FXML private Button addEntryBt;
    @FXML private Button increaseQuantityBt;
    @FXML private Button decreaseQuantityBt;
    @FXML private Button refreshBt;
    @FXML private Button deleteBt;
    @FXML private TextField fieldFilter;
    @FXML private TableView<StockItem> table = new TableView<>();
    @FXML private TableColumn<StockItem, String> col_id;
    @FXML private TableColumn<StockItem, String> col_name;
    @FXML private TableColumn<StockItem, Integer> col_quantity;
    @FXML private TableColumn<StockItem, Category> col_category;
    @FXML private TableColumn<StockItem, Size> col_size;
    @FXML private MenuItem aboutBt;
    @FXML private MenuItem homeBt;
    @FXML private MenuItem editBt;
    @FXML private Text currentLogged;

    private ObservableList<StockItem> masterData = FXCollections.observableArrayList();
    private StockItemRepository stockItemRepository = RepositoryAdmin.getStockItemRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<StockItem> items = StockItemRepository.buildListFromDB();

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onHomeBt() {
        mainPane.setCenter(centerPane);
    }

    @FXML
    public void onAddEntryBt() {
        try {
            URL url = new File("src\\application\\gui\\EntryView.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Stage stage = new Stage();
            stage.setTitle("Stock Control");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (MalformedURLException throwables) { Alerts.showAlert("URL Error", null,
                "An exception occurred forming the URL.", Alert.AlertType.ERROR);
        } catch (IOException e) { Alerts.showAlert("I/O Error", null,
                "A I/O exception occurred while loading the URL.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onEditEntryBt() {
        try {
            StockItem item = table.getSelectionModel().getSelectedItem();
            if (item == null) throw new MissingSelectionException("One item from table must be selected.");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditView.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Stock Control");
            stage.setScene(new Scene(loader.load()));
            stage.show();

            EditViewController controller = loader.getController();
            controller.updateFields(item);
        } catch (MissingSelectionException throwables) {
            Alerts.showAlert("Missing Selected Item", null, throwables.getMessage() , Alert.AlertType.ERROR);
        } catch(IOException throwables) { Alerts.showAlert("I/O Error", null,
                "A I/O exception occurred while loading the URL.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onIncreaseQuantityBt() {
        try {
            StockItem item = table.getSelectionModel().getSelectedItem();
            if (item == null) throw new MissingSelectionException("One item from table must be selected.");

            item.setQuantity(item.getQuantity() + 1);

            stockItemRepository.updateQuantity(item);
            table.refresh();
        } catch (MissingSelectionException e) {
            Alerts.showAlert("Missing Selected Item", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onDecreaseQuantityBt() {
        try {
            StockItem item = table.getSelectionModel().getSelectedItem();
            if (item == null) throw new MissingSelectionException("One item from table must be selected.");

            item.setQuantity(item.getQuantity() - 1);

            if (item.getQuantity() >= 0) stockItemRepository.updateQuantity(item);
            else {
                Alerts.showAlert("Negative Quantity", null, "Item quantity may not be negative.",
                        Alert.AlertType.ERROR);
                item.setQuantity(0);
            }
            table.refresh();
        } catch (MissingSelectionException e) {
            Alerts.showAlert("Missing Selected Item", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onDeleteBt() {
        try {
            StockItem item = table.getSelectionModel().getSelectedItem();
            if (item == null) throw new MissingSelectionException("One item from table must be selected.");

            stockItemRepository.delete(item);
            List<StockItem> items = StockItemRepository.buildListFromDB();

            masterData.removeAll(masterData);
            masterData.addAll(items);
        } catch (MissingSelectionException e) {
            Alerts.showAlert("Missing Selected Item", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onRefreshBt() {
        List<StockItem> items = StockItemRepository.buildListFromDB();
        masterData.removeAll(masterData);
        masterData.addAll(items);
    }
}
