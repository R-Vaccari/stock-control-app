package application.gui;

import application.RequiredFieldException;
import application.database.SQL;
import application.entities.StockItem;
import application.entities.enums.Category;
import application.entities.enums.Size;
import application.gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EntryViewController implements Initializable {

    @FXML
    private Button registerBt;
    @FXML
    private TextField txt01;
    @FXML
    private TextField txt02;
    @FXML
    private TextField txt03;
    @FXML
    private TextField txt04;
    @FXML
    private TextField txt05;
    /*
    @FXML
    private MenuItem menuItem1 = new MenuItem("Lugagge");
    @FXML
    private MenuItem menuItem2 = new MenuItem("Backpack");
    @FXML
    private MenuItem menuItem3 = new MenuItem("Wallet");
    @FXML
    private MenuButton menuButton01 = new MenuButton("Category", null, menuItem1, menuItem2, menuItem3);
     */

    @FXML
    public void onRegisterBt() {
        try {
            if (txt01.getText().equals("") || txt02.getText().equals("") || txt03.getText().equals("")) throw new RequiredFieldException();
            else {
                StockItem item = new StockItem(txt01.getText(), txt02.getText(), Integer.parseInt(txt03.getText()),
                        Category.valueOf(txt04.getText().toUpperCase()), Size.valueOf(txt05.getText().toUpperCase()));

                SQL.executeSQL(item);
                Alerts.showAlert("Entry Registration", null, "Entry registered successfully. Use 'Refresh' to see changes.",
                        Alert.AlertType.INFORMATION);
                closeStage();
            }
        } catch (RequiredFieldException e) {
            Alerts.showAlert("Fields Missing", null, "Id, Name and Quantity fields must be filled.",
                    Alert.AlertType.ERROR);
        }
    }

    public void closeStage() {
        Stage stage = (Stage) registerBt.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
