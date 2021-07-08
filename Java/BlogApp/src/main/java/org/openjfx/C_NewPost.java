package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class C_NewPost {
    public final int WIN_WIDTH = 600;
    public final int WIN_HEIGHT = 640;

    private Parent root;
    private FXMLLoader loader;

    // Category
    @FXML
    private TextField textFieldAddCategory;

    // Post
    @FXML
    private RadioButton radioButtonPublished;
    @FXML
    private RadioButton radioButtonDraft;
    @FXML
    private ComboBox<String> comboBoxCategory;
    @FXML
    private TextField textFieldTitle;
    @FXML
    private TextArea textAreaText;
    @FXML
    private TextField textFieldImageUrl;

    @FXML
    public void initialize() {
        updateCategories();
    }

    public void updateCategories() {
        List<String> categories = Database.getCategories();
        comboBoxCategory.getItems().clear();
        for(String str : categories)
            comboBoxCategory.getItems().add(str);
        comboBoxCategory.setValue("Sonstiges");
    }

    @FXML
    public void addCategory() {
        if (textFieldAddCategory.getText().isEmpty()) {
            System.out.println("Bitte eine Kategorie eintragen!");
        } else {
            Database.addCategory(textFieldAddCategory.getText());
            textFieldAddCategory.clear();
            updateCategories();
        }
    }

    @FXML
    public void addPost() {
        boolean published = radioButtonPublished.isSelected();
        String category = comboBoxCategory.getValue();
        String title = textFieldTitle.getText();
        String text = textAreaText.getText();
        String imageUrl = ImageHandling.addImage(textFieldImageUrl.getText());
        System.out.println(textFieldImageUrl.getText());

        if(category.isEmpty())
            category = "Sonstiges";

        if(title.isEmpty())
            title = "Ohne Titel";

        if(text.isEmpty())
            text = "Hier könnte ihre Werbung stehen.";

        Database.addPost(category, title, text, imageUrl, published);

        radioButtonPublished.setSelected(true);
        textFieldTitle.clear();
        textAreaText.clear();
        textFieldImageUrl.clear();
    }

    // TODO Dateiauswahl Bild beschränken/überprüfen (jpg, png);
    // TODO Scene sperren, während FileChooser geöffnet ist
    @FXML
    public void loadImage() {
        Stage stage = new Stage();
        String imagePath;
        FileChooser fileChooser = new FileChooser();

        imagePath = fileChooser.showOpenDialog(stage).toURI().toString();
        imagePath = imagePath.replace("file:/", "");
        textFieldImageUrl.setText(imagePath);
    }

    @FXML
    private void switchToReader() throws IOException {
        loader =  new FXMLLoader(getClass().getResource("Reader.fxml"));
        root = loader.load();
        C_Reader controller = loader.getController();
        Scene scene = new Scene(root, controller.PREF_WIN_WIDTH, controller.PREF_WIN_HEIGHT);
        App.setScene(scene);
    }

    @FXML
    public void switchToEditBlog() throws IOException {
        loader =  new FXMLLoader(getClass().getResource("EditBlog.fxml"));
        root = loader.load();
        C_EditBlog controller = loader.getController();
        Scene scene = new Scene(root, controller.WIN_WIDTH, controller.WIN_HEIGHT);
        App.setScene(scene);
    }
}