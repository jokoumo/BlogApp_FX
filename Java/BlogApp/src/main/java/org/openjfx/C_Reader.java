package org.openjfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class C_Reader {
    public final double PREF_WIN_WIDTH = 600d;
    public final double PREF_WIN_HEIGHT = 960d;

    private Blogpost post;
    private Parent root;
    private FXMLLoader loader;
    private static int currentPost = -1;
    private List<Integer> postID = new LinkedList<>();
    private Image image;
    private Stage stage;

    @FXML
    private ImageView imageViewPost;
    @FXML
    private Label labelPostTitle;
    @FXML
    private Label labelPostInfo;
    @FXML
    private Label labelPostCategory;
    @FXML
    private Label labelPostCount;
    @FXML
    private TextArea textAreaPostText;
    @FXML
    private Button buttonNext;
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonOldestPost;
    @FXML
    private Button buttonNewestPost;

    @FXML
    private void initialize() throws SQLException {
        postID = Database.getPublishedPostId();

        if(postID.contains(Blogpost.getCurrentId())) {
            currentPost = postID.indexOf(Blogpost.getCurrentId());
            Blogpost.setCurrentId(-1);
        } else if(currentPost > (postID.size() -1) || currentPost < 0)
            currentPost = postID.size() -1;

        if(currentPost == 0) {
            buttonNext.setDisable(true);
            buttonOldestPost.setDisable(true);
        }

        if(currentPost == (postID.size() -1)) {
            buttonBack.setDisable(true);
            buttonNewestPost.setDisable(true);
        }

        Platform.runLater(() -> {
            showPost();
        });
    }

    @FXML
    public void buttonControl(ActionEvent event) throws SQLException {
        if(event.getSource().equals(buttonNext) && currentPost > 0)
            currentPost--;
        if(event.getSource().equals(buttonBack) && currentPost < postID.size() - 1)
            currentPost++;
        if(event.getSource().equals(buttonNewestPost))
            currentPost = postID.size() - 1;
        if(event.getSource().equals(buttonOldestPost))
            currentPost = 0;

        if(buttonNext.isDisabled() && currentPost > 0) {
            buttonNext.setDisable(false);
            buttonOldestPost.setDisable(false);
        }

        if(buttonBack.isDisabled() && currentPost < postID.size() - 1) {
            buttonBack.setDisable(false);
            buttonNewestPost.setDisable(false);
        }

        if(currentPost == 0) {
            buttonNext.setDisable(true);
            buttonOldestPost.setDisable(true);
        }

        if(currentPost == postID.size() - 1) {
            buttonBack.setDisable(true);
            buttonNewestPost.setDisable(true);
        }

        showPost();
    }

    @FXML
    public void showPost() {
        post = Database.getPost(postID.get(currentPost));

        labelPostCount.setText("Artikel " + (postID.size() - currentPost) + "/" + postID.size());
        labelPostTitle.setText(post.getTitle());
        labelPostCategory.setText(post.getCategory());
        labelPostInfo.setText("Von " + post.getAuthor() + " am " + post.getDate() + " um " + post.getTime() + " Uhr.");
        textAreaPostText.setText(post.getText());

        try {
            image = new Image(getClass().getResourceAsStream(post.getImageUrl()));
            imageViewPost.setImage(image);
            double ratio = image.getWidth() / image.getHeight();
            imageViewPost.setFitWidth(imageViewPost.getFitHeight() * ratio);
            imageViewPost.setLayoutX((PREF_WIN_WIDTH - imageViewPost.getFitWidth()) / 2);
        } catch (NullPointerException e) {
            System.out.println("Keine Bilddatei \"" + post.getImageUrl() + "\" gefunden");
        }
    }

    public void deletePost() {
        if(Database.deletePost(postID.get(currentPost))) {
            postID = Database.getPublishedPostId();
            while (currentPost >= postID.size())
                currentPost--;
            showPost();
        }
    }

    @FXML
    public void switchToEditBlog() throws IOException {
        loader =  new FXMLLoader(getClass().getResource("EditBlog.fxml"));
        root = loader.load();
        C_EditBlog controller = loader.getController();
        Scene scene = new Scene(root, controller.WIN_WIDTH, controller.WIN_HEIGHT);
        App.setScene(scene);
    }

    @FXML
    public void switchToNewPost() throws IOException {
        loader =  new FXMLLoader(getClass().getResource("NewPost.fxml"));
        root = loader.load();
        C_NewPost controller = loader.getController();
        Scene scene = new Scene(root, controller.WIN_WIDTH, controller.WIN_HEIGHT);
        App.setScene(scene);
    }

    @FXML
    public void switchToUserProfile() throws IOException {
        loader =  new FXMLLoader(getClass().getResource("UserProfile.fxml"));
        root = loader.load();
        C_UserProfile controller = loader.getController();
        Scene scene = new Scene(root, controller.WIN_WIDTH, controller.WIN_HEIGHT);
        App.setScene(scene);
    }

    @FXML
    public void switchToLogin() throws IOException {
        UserProfile.clearUserProfile();

        loader =  new FXMLLoader(getClass().getResource("Login.fxml"));
        root = loader.load();
        C_Login controller = loader.getController();
        Scene scene = new Scene(root, controller.WIN_WIDTH, controller.WIN_HEIGHT);
        App.setScene(scene);
    }

    @FXML
    public void closeApp() {
        App.closeApp();
    }
}
