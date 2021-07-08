package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class C_EditPost {
    public final int WIN_WIDTH = 600;
    public final int WIN_HEIGHT = 640;

    private Parent root;
    private FXMLLoader loader;
    private List<String> categories;

    @FXML
    private TextField textFieldTitle;
    @FXML
    private TextField textFieldImageUrl;
    @FXML
    private TextArea textAreaText;
    @FXML
    private RadioButton radioButtonPublished;
    @FXML
    private RadioButton radioButtonDraft;
    @FXML
    private ComboBox<String>  comboBoxCategory;


    @FXML
    private void initialize() {
        Blogpost post = Database.getPost(Blogpost.getCurrentId());
        File file = new File(post.getImageUrl());
        List<String> categories = Database.getCategories();
        textFieldImageUrl.setPromptText(file.getName());
        textFieldTitle.setText(post.getTitle());
        textAreaText.setText(post.getText());

        comboBoxCategory.getItems().clear();
        for(String str : categories)
            comboBoxCategory.getItems().add(str);
        comboBoxCategory.setValue(post.getCategory());

        if(!post.getPublishedBool())
            radioButtonDraft.setSelected(true);
    }

    @FXML
    private void savePost() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Änderungen speichern");
        alert.setHeaderText("Alle Änderungen speichern?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            Blogpost Post = Database.getPost(Blogpost.getCurrentId());

            String title;
            if (textFieldTitle.getText().isEmpty())
                title = Post.getTitle();
            else
                title = textFieldTitle.getText();

            String text;
            if (textAreaText.getText().isEmpty())
                text = Post.getText();
            else
                text = textAreaText.getText();

            String category = comboBoxCategory.getValue();
            boolean published = radioButtonPublished.isSelected();

            String imageUrl;
            if (textFieldImageUrl.getText().isEmpty())
                imageUrl = Post.getImageUrl();
            else
                imageUrl = ImageHandling.addImage(textFieldImageUrl.getText());

            Database.editPost(new Blogpost(Blogpost.getCurrentId(), category, title, text, imageUrl, published));
        }
    }

    @FXML
    private void loadImage() {
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
