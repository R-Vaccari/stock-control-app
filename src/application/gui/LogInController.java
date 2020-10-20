package application.gui;

import application.entities.User;
import application.exceptions.UnrecognizedUserException;
import application.gui.util.Alerts;
import application.repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML private AnchorPane pane;
    @FXML private Button logInButton;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPass;
    @FXML private ImageView logo;
    @FXML private Text errorText;

    public void onLogInButton() {
        try {
            User user = UserRepository.findByUsername(txtUsername.getText());
            if (user != null && user.getPassword().equals(txtPass.getText())) {
                loadMain();
                closeStage();
            } else { throw new UnrecognizedUserException("Wrong username or password."); }
        } catch (UnrecognizedUserException e) {
            errorText.setText("Wrong username or password.");
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
        pane.setStyle("-fx-background-color: linear-gradient(to bottom right, #2f4f4f, #006400);");
        Image image = null;
        try {
            image = new Image(new FileInputStream("src\\resources\\box.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logo.setImage(image);

    }
}
