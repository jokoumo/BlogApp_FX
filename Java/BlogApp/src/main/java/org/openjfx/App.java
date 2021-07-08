package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        scene = new Scene(loadFXML("Login"));
        Image icon = new Image(getClass().getResourceAsStream("images/icon.jpg"));
        
        stage.getIcons().add(icon);
        stage.setTitle("BlogApp");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        //stage.setOnCloseRequest(event ->);
    }

//    static void setRoot(String fxml) throws IOException {
//        scene.setRoot(loadFXML(fxml));
//    }

    static void setScene(Scene newScene) {
        double oldX = stage.getX() + stage.getWidth() / 2;
        double oldY = stage.getY() + stage.getHeight() / 2;

        scene = newScene;
        stage.setScene(scene);

        double newX = stage.getX() + stage.getWidth() / 2;
        double newY = stage.getY() + stage.getHeight() / 2;

        // Fenster-Ursprung mittig setzen
        stage.setX(stage.getX() - (newX - oldX));
        //stage.setY(stage.getY() - (newY - oldY));

        // Fenster ins Bild rücken, falls außerhalb (ungünstig mit Multiscreen)
//        if(stage.getX() < 0)
//            stage.setX(0);
//        if(stage.getX() > Screen.getPrimary().getBounds().getMaxX() - stage.getWidth())
//            stage.setX(Screen.getPrimary().getBounds().getMaxX() - stage.getWidth());
        if(stage.getY() < 0)
            stage.setY(0);
        if(stage.getY() > Screen.getPrimary().getBounds().getMaxY() - stage.getHeight())
            stage.setY(Screen.getPrimary().getBounds().getMaxY() - stage.getHeight());

        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void closeApp() {
        stage.close();
    }

//    @Override
//    public void init() throws Exception {
//        super.init();
//    }
//
//    @Override
//    public void stop() throws Exception {
//        super.stop();
//    }

    public static void main(String[] args) {
        launch();
    }
}