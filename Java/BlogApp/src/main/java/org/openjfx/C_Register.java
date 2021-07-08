package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;

public class C_Register {
    public final int WIN_WIDTH = 325;
    public final int WIN_HEIGHT = 375;

    private Parent root;
    private FXMLLoader loader;

    @FXML
    private Label labelInfo;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldCountry;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField passwordField_1;
    @FXML
    private PasswordField passwordField_2;
    @FXML
    private DatePicker datePicker;

    @FXML
    private void initialize() {
        datePicker.setValue(LocalDate.of(1995, 1, 1));
        labelInfo.setVisible(false);
    }

    @FXML
    public void register() {
        labelInfo.setVisible(true);
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String country = textFieldCountry.getText();
        String email = textFieldEmail.getText().toLowerCase();
        String pw_1 = passwordField_1.getText();
        String pw_2 = passwordField_2.getText();
        String birthDate = datePicker.getValue().toString();

        if(firstName.isEmpty() || lastName.isEmpty() ||
            country.isEmpty() || email.isEmpty() ||
            pw_1.isEmpty() || pw_2.isEmpty())
            labelInfo.setText("Bitte alle Felder ausfüllen.");
        else if (Database.getEmail(textFieldEmail.getText()))
            labelInfo.setText("Email-Adresse wird bereits verwendet.");
        else if(textFieldEmail.getText().indexOf("@") == -1 ||
                textFieldEmail.getText().lastIndexOf(".") < textFieldEmail.getText().indexOf("@"))
            labelInfo.setText("Ungültige Email-Adresse.");
        else if(!passwordField_1.getText().equals(passwordField_2.getText()))
            labelInfo.setText("Passwörter stimmen nicht überein.");
        else {
            Database.addAuthor(firstName, lastName, birthDate, country, email, EncryptDecrypt.encrypt(pw_1));
            labelInfo.setText("Profil angelegt.");
            clearFields();
        }
    }

    public void clearFields() {
        textFieldFirstName.clear();
        textFieldLastName.clear();
        textFieldCountry.clear();
        textFieldEmail.clear();
        passwordField_1.clear();
        passwordField_2.clear();
        datePicker.setValue(LocalDate.of(1995, 1, 1));
    }

    @FXML
    public void switchToLogin() throws IOException {
        loader =  new FXMLLoader(getClass().getResource("Login.fxml"));
        root = loader.load();
        C_Login controller = loader.getController();
        Scene scene = new Scene(root, controller.WIN_WIDTH, controller.WIN_HEIGHT);
        App.setScene(scene);
    }
}
