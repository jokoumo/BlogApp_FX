package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class C_EditBlog {
    public final int WIN_WIDTH = 800;
    public final int WIN_HEIGHT = 800;

    private Parent root;
    private FXMLLoader loader;

    @FXML
    private Button buttonResetSearch;
    @FXML
    private TextField textFieldSearchPost;
    @FXML
    private TableView<Blogpost> tableViewPosts;
    @FXML
    private TableColumn<Blogpost, Integer> tableColumnPostsID;
    @FXML
    private TableColumn<Blogpost, String> tableColumnPostsTitle;
    @FXML
    private TableColumn<Blogpost, String> tableColumnPostsAuthor;
    @FXML
    private TableColumn<Blogpost, String> tableColumnPostsDate;
    @FXML
    private TableColumn<Blogpost, String> tableColumnPostsPublished;
    @FXML
    private ChoiceBox<String> choiceBoxSearchCategory;

    @FXML
    private void initialize() {
        //ObservableList<Blogpost> posts = Database.getPostOverview();

        tableColumnPostsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnPostsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumnPostsAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tableColumnPostsDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnPostsPublished.setCellValueFactory(new PropertyValueFactory<>("published"));
        tableViewPosts.setItems(Database.getPostOverview());

        choiceBoxSearchCategory.getItems().add("Titel");
        choiceBoxSearchCategory.getItems().add("Autor");
        choiceBoxSearchCategory.getItems().add("Datum");
        choiceBoxSearchCategory.setValue("Titel");

        buttonResetSearch.setVisible(false);
        textFieldSearchPost.clear();
    }

    @FXML
    private void deletePost() {
        Blogpost post;
        post = tableViewPosts.getSelectionModel().getSelectedItem();

        if(Database.deletePost(post.getId()))
            tableViewPosts.getItems().remove(post);
    }

    @FXML
    private void searchPost() {
        ObservableList<Blogpost> posts = Database.getPostOverview();
        ObservableList<Blogpost> searchedPosts = FXCollections.observableArrayList();

        String searchCategory = choiceBoxSearchCategory.getValue();

        if(textFieldSearchPost.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Beitrag suchen");
            alert.setHeaderText("Bitte zuerst einen Suchtext eingeben");
        } else {
            tableViewPosts.getItems().clear();
            for (Blogpost post : posts) {
                switch(searchCategory) {
                    case "Titel":
                        if (post.getTitle().toLowerCase().contains(textFieldSearchPost.getText().toLowerCase()))
                            searchedPosts.add(post);
                        break;
                    case "Autor":
                        if (post.getAuthor().toLowerCase().contains(textFieldSearchPost.getText().toLowerCase()))
                            searchedPosts.add(post);
                        break;
                    case "Datum":
                        if (post.getDate().contains(textFieldSearchPost.getText()))
                            searchedPosts.add(post);
                        break;
                    default:
                }
            }
            tableViewPosts.setItems(searchedPosts);
            buttonResetSearch.setVisible(true);
        }
    }

    @FXML
    private void readPost() throws IOException {
        if(tableViewPosts.getSelectionModel().getSelectedItem() != null) {
            Blogpost post;
            post = tableViewPosts.getSelectionModel().getSelectedItem();
            Blogpost.setCurrentId(post.getId());
            switchToReader();
        }
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
    private void switchToNewPost() throws IOException {
        loader =  new FXMLLoader(getClass().getResource("NewPost.fxml"));
        root = loader.load();
        C_NewPost controller = loader.getController();
        Scene scene = new Scene(root, controller.WIN_WIDTH, controller.WIN_HEIGHT);
        App.setScene(scene);
    }

    @FXML
    private void switchToEditPost() throws IOException {
        Blogpost post;
        post = tableViewPosts.getSelectionModel().getSelectedItem();
        Blogpost.setCurrentId(post.getId());

        loader =  new FXMLLoader(getClass().getResource("EditPost.fxml"));
        root = loader.load();
        C_EditPost controller = loader.getController();
        Scene scene = new Scene(root, controller.WIN_WIDTH, controller.WIN_HEIGHT);
        App.setScene(scene);
    }
}
