package application.gui.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SceneAdmin {

    public Parent loadSceneByURL(String target) {
        try {
            URL url = new File(target).toURI().toURL();
            Parent root = FXMLLoader.load(url);

            return root;
        } catch (MalformedURLException throwables) { Alerts.showAlert("URL Error", null,
                "An exception occurred forming the URL.", Alert.AlertType.ERROR);
        } catch (IOException e) { Alerts.showAlert("I/O Error", null,
                "A I/O exception occurred while loading the URL.", Alert.AlertType.ERROR);
        }
        return null;
    }
}
