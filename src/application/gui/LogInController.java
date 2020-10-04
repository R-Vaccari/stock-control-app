package application.gui;

import application.gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML private AnchorPane pane;
    @FXML private Button logInButton;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPass;

    public void onLogInButton() {
        if (txtUsername.getText().equals("admin") && txtPass.getText().equals("123456")) {
            loadMain();
            closeStage();
        } else {
            Alerts.showAlert("Sign In Error", null, "Wrong username or password.",
                    Alert.AlertType.ERROR);
        }
    }

    public void closeStage() {
        Stage stage = (Stage) logInButton.getScene().getWindow();
        stage.close();
    }

    public void loadMain() {
        try {
            URL url = new File("src\\application\\gui\\MainView.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Stage stage = new Stage();
            stage.setTitle("Stock Control");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException throwables) {
            Alerts.showAlert("I/O Error", null, "Error loading MainView.fxml.",
                    Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setStyle("-fx-background-color: darkgreen;");

    }
}
