package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Map;

public class C_Login {
    public final int WIN_WIDTH = 325;
    public final int WIN_HEIGHT = 290;

    private Parent root;
    private FXMLLoader loader;

    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label labelInfo;

    @FXML
    private void initialize() throws IOException {
        labelInfo.setVisible(false);
    }

    @FXML
    public void login() throws IOException {
        boolean valid = false;
        if(textFieldEmail.getText().isEmpty() || passwordField.getText().isEmpty()) {
            labelInfo.setVisible(true);
            labelInfo.setText("Bitte Email und Password eingeben.");
        } else {
            valid = Database.getLogin(textFieldEmail.getText(), passwordField.getText());
        }

        if(valid) {
            Map<String, String> profile = Database.getAuthor(textFieldEmail.getText());
            UserProfile.setUserProfile(profile.get("firstName"), profile.get("lastName"), profile.get("birthDate"),
                    profile.get("country"), profile.get("email"), profile.get("joiningDate"));
            switchToReader();
        } else {
            labelInfo.setVisible(true);
            labelInfo.setText("Email oder Password falsch.");
            passwordField.clear();
        }
    }

    @FXML
    public void switchToRegister() throws IOException {
        loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        root = loader.load();
        C_Register controller = loader.getController();
        Scene scene = new Scene(root, controller.WIN_WIDTH, controller.WIN_HEIGHT);
        App.setScene(scene);
    }

    @FXML
    public void switchToReader() throws IOException {
        loader =  new FXMLLoader(getClass().getResource("Reader.fxml"));
        root = loader.load();
        C_Reader controller = loader.getController();
        Scene scene = new Scene(root, controller.PREF_WIN_WIDTH, controller.PREF_WIN_HEIGHT);
        App.setScene(scene);
    }

    @FXML
    public void closeApp() {
        App.closeApp();
    }
}
