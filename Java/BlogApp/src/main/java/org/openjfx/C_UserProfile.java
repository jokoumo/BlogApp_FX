package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class C_UserProfile {
    public final int WIN_WIDTH = 600;
    public final int WIN_HEIGHT = 450;

    private Parent root;
    private FXMLLoader loader;

    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonChangePw;
    @FXML
    private Button buttonSavePw;
    @FXML
    private Button buttonDiscardPw;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldBirthDate;
    @FXML
    private TextField textFieldCountry;
    @FXML
    private TextField textFieldJoiningDate;
    @FXML
    private PasswordField pwFieldOld;
    @FXML
    private PasswordField pwFieldNew_1;
    @FXML
    private PasswordField pwFieldNew_2;
    @FXML
    private Label labelInfoPw;


    @FXML
    private void initialize() {
        setPwControlsVisible(false);
        labelInfoPw.setVisible(false);

        textFieldFirstName.setText(UserProfile.getFirstName());
        textFieldLastName.setText(UserProfile.getLastName());
        textFieldEmail.setText(UserProfile.getEmail());
        textFieldBirthDate.setText(UserProfile.getBirthDate());
        textFieldCountry.setText(UserProfile.getCountry());
        textFieldJoiningDate.setText(UserProfile.getJoiningDate());
    }

    @FXML
    public void changePassword() {
        setPwControlsVisible(true);
    }

    @FXML
    public void savePassword() {
        if(pwFieldOld.getText().isEmpty() || pwFieldNew_1.getText().isEmpty() || pwFieldNew_1.getText().isEmpty())
            labelInfoPw.setText("Bitte alle Felder ausfüllen");
        else if(!Database.checkPassword(pwFieldOld.getText()))
            labelInfoPw.setText("Altes Passwort ist falsch.");
        else if(!pwFieldNew_1.getText().equals(pwFieldNew_2.getText()))
            labelInfoPw.setText("Neuen Passwörter stimmen nicht überein.");
        else {
            Database.setPassword(EncryptDecrypt.encrypt(pwFieldNew_1.getText()));
            labelInfoPw.setText("Gespeichert.");
            setPwControlsVisible(false);
        }

        labelInfoPw.setVisible(true);
    }

    @FXML
    public void discardPassword() {
        setPwControlsVisible(false);
    }

    @FXML
    public void setPwControlsVisible(boolean visible) {
        if(visible) {
            pwFieldOld.setVisible(true);
            pwFieldNew_1.setVisible(true);
            pwFieldNew_2.setVisible(true);
            buttonChangePw.setVisible(false);
            buttonSavePw.setVisible(true);
            buttonDiscardPw.setVisible(true);
        } else {
            pwFieldOld.clear();
            pwFieldNew_1.clear();
            pwFieldNew_2.clear();
            pwFieldOld.setVisible(false);
            pwFieldNew_1.setVisible(false);
            pwFieldNew_2.setVisible(false);
            buttonChangePw.setVisible(true);
            buttonSavePw.setVisible(false);
            buttonDiscardPw.setVisible(false);
        }
    }

    @FXML
    public void switchToReader() throws IOException {
        loader =  new FXMLLoader(getClass().getResource("Reader.fxml"));
        root = loader.load();
        C_Reader controller = loader.getController();
        Scene scene = new Scene(root, controller.PREF_WIN_WIDTH, controller.PREF_WIN_HEIGHT);
        App.setScene(scene);
    }
}
