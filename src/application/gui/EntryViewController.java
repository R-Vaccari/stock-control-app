package application.gui;

import application.DBConnector;
import application.entities.StockItem;
import application.entities.enums.Category;
import application.entities.enums.Size;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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

    public void onRegisterBt() throws SQLException {

        StockItem item = new StockItem(txt01.getText(), txt02.getText(), Integer.parseInt(txt03.getText()),
                Category.valueOf(txt04.getText()), Size.valueOf(txt05.getText()));

        System.out.println(item);

        Stage stage = (Stage) registerBt.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
