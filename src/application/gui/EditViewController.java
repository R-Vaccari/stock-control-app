package application.gui;

import application.database.SQL;
import application.entities.StockItem;
import application.entities.enums.Category;
import application.entities.enums.Size;
import application.exceptions.RequiredFieldException;
import application.gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditViewController implements Initializable {

    @FXML
    private Button updateBt;
    @FXML
    private TextField txt01;
    @FXML
    private TextField txt02;
    @FXML
    private TextField txt03;
    @FXML
    private TextField txt04;
    @FXML
    private Text idField;

    @FXML
    public void onUpdateBt() {
        try {
            if (txt01.getText().equals("") || txt02.getText().equals("")) throw new RequiredFieldException();
            else {
                StockItem item = new StockItem(idField.getText(), txt01.getText(), Integer.parseInt(txt02.getText()),
                        Category.valueOf(txt03.getText().toUpperCase()), Size.valueOf(txt04.getText().toUpperCase()));

                SQL.updateItemSQL(item);
                Alerts.showAlert("Entry Update", null, "Entry '" + item.getName() +  "' updated successfully. Use 'Refresh' to see changes.",
                        Alert.AlertType.INFORMATION);
                closeStage();
            }
        } catch (RequiredFieldException e) {
            Alerts.showAlert("Fields Missing", null, "Id, Name and Quantity fields must be filled.",
                    Alert.AlertType.ERROR);
        }
    }

    public void updateFields(StockItem item) {
        txt01.setText(item.getName());
        txt02.setText(item.getQuantity().toString());
        txt03.setText(item.getCategory().toString());
        txt04.setText(item.getSize().toString());
        idField.setText(item.getId());
    }

    public void closeStage() {
        Stage stage = (Stage) updateBt.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
