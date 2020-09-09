package application.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AboutView extends AnchorPane {

    public AboutView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("src\\application\\gui\\AboutView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
