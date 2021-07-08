package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Database {
    private static final String dbPath = "jdbc:sqlite:src\\main\\resources\\org\\openjfx\\blogapp.db";

    public static boolean checkPassword(String pw) {
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery("SELECT password FROM Authors WHERE email = '" + UserProfile.getEmail() + "';")) {

            if(resSet.next())
                return (EncryptDecrypt.checkPassword(pw, resSet.getString("password")));

        } catch (SQLException e) {
            System.out.println("Fehler Get Login: " + e.getMessage());
        }
        return false;
    }

    public static boolean setPassword(String pw) {
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("UPDATE Authors SET password = '" + pw +
                                "' WHERE email = '" + UserProfile.getEmail() + "';");

        } catch (SQLException e) {
            System.out.println("Fehler Set Password: " + e.getMessage());
        }
        return false;
    }

    public static boolean getLogin(String email, String pw) {
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery("SELECT password FROM Authors " +
                                                        "WHERE email = '" + email + "';")) {

            if(resSet.next())
                return (EncryptDecrypt.checkPassword(pw, resSet.getString("password")));

        } catch (SQLException e) {
            System.out.println("Fehler Get Login: " + e.getMessage());
        }
        return false;
    }

    public static void addAuthor(String firstName, String lastName, String birthDate,
                                 String country, String email, String pw) {
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("INSERT INTO Authors (firstName, lastName, birthDate, " +
                    "country, email, password, joiningDate)" +
                    " VALUES ('" + firstName + "','" + lastName + "','" + birthDate + "','" + country + "','" +
                    email + "','" + pw + "','" + Date.valueOf(LocalDate.now().toString()) + "');");

        } catch (SQLException e) {
            System.out.println("Fehler Add Author: " + e.getMessage());
        }
    }

    public static Map<String, String> getAuthor(String email) {
        Map<String, String> profile = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery("SELECT " +
                     "firstName, lastName, birthDate, country, email, joiningDate FROM Authors " +
                     "WHERE email = '" + email + "';")) {

            if (resSet.next()) {
                profile.put("firstName", resSet.getString("firstName"));
                profile.put("lastName", resSet.getString("lastName"));
                profile.put("birthDate", resSet.getString("birthDate"));
                profile.put("country", resSet.getString("country"));
                profile.put("email", resSet.getString("email"));
                profile.put("joiningDate", resSet.getString("joiningDate"));
            }

        } catch (SQLException e) {
            System.out.println("Fehler Get Email: " + e.getMessage());
        }

        return profile;
    }

    public static boolean getEmail(String email) {
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery("SELECT email FROM Authors " +
                     "WHERE email = '" + email + "';")) {

            if (resSet.next())
                return true;

        } catch (SQLException e) {
            System.out.println("Fehler Get Email: " + e.getMessage());
        }

        return false;
    }

    public static void addCategory(String category) {
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("INSERT INTO Categories (categoryName)" +
                    " VALUES ('" + category + "');");

        } catch (SQLException e) {
            System.out.println("Fehler Add Category: " + e.getMessage());
        }
    }

    public static List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery("SELECT * FROM Categories ORDER BY categoryName ASC;")) {

            while(resSet.next())
                categories.add(resSet.getString("categoryName"));

        } catch (SQLException e) {
            System.out.println("Fehler Get Category: " + e.getMessage());
        }

        return categories;
    }

    public static void addPost(String category, String title, String text, String imageUrl, boolean published) {
        String author = UserProfile.getUserName();
        String date = Date.valueOf(LocalDate.now()).toString();
        String time = Time.valueOf(LocalTime.now()).toString();

        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("INSERT INTO Posts (author, category, title, text, date, time, imageUrl, published)" +
                    " VALUES ('" + author + "','" + category +  "','" + title + "','" +
                    text + "','" + date + "','" + time + "','" + imageUrl + "'," + published + ");");

        } catch (SQLException e) {
            System.out.println("Fehler Add Post: " + e.getMessage());
        }
    }

    public static void editPost(Blogpost post) {
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("UPDATE Posts SET " +
                    " category = '" + post.getCategory() +
                    "', title = '" + post.getTitle() +
                    "', text = '" + post.getText() +
                    "', imageUrl = '" + post.getImageUrl() +
                    "', published = " + post.getPublishedBool() +
                    " WHERE _id = " + post.getId() + ";");

        } catch (SQLException e) {
            System.out.println("Fehler Add Post: " + e.getMessage());
        }
    }

    public static List<Integer> getPublishedPostId() {
        List<Integer> postId = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery("SELECT _id FROM Posts WHERE published = 1;")) {

            while(resSet.next())
                postId.add(resSet.getInt("_id"));

        } catch (SQLException e) {
            System.out.println("Fehler Get Post IDs: " + e.getMessage());
        }
        return postId;
    }

    public static Blogpost getPost(int id) {
        Blogpost post = null;
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery("SELECT * FROM Posts WHERE _id = " + id + ";")) {

            if(resSet.next()) {
                post = new Blogpost(resSet.getInt("_id"),
                        resSet.getString("author"),
                        resSet.getString("category"),
                        resSet.getString("title"),
                        resSet.getString("text"),
                        resSet.getString("date"),
                        resSet.getString("time"),
                        resSet.getString("imageUrl"),
                        resSet.getBoolean("published"));
            }

        } catch (SQLException e) {
            System.out.println("Fehler Get Post: " + e.getMessage());
        }
        return post;
    }

    public static ObservableList<Blogpost> getPostOverview() {
        ObservableList<Blogpost> posts = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery("SELECT _id, author, title, date, published FROM Posts ORDER BY _id DESC;")) {

            while(resSet.next())
                posts.add(new Blogpost(resSet.getInt("_id"), resSet.getString("author"),
                        resSet.getString("title"), resSet.getString("date"),
                        resSet.getBoolean("published")));

        } catch (SQLException e) {
            System.out.println("Fehler Get Post Overview: " + e.getMessage());
        }

        return posts;
    }

    public static boolean deletePost(int id) {
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement()
             ) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Beitrag löschen");
            alert.setHeaderText("Soll dieser Beitrag dauerhaft gelöscht werden?");
            //alert.setContentText("");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                statement.executeUpdate("DELETE FROM Posts WHERE _id = " + id + ";");
                return true;
            } else
                return false;

        } catch (SQLException e) {
            System.out.println("Fehler Delete Post: " + e.getMessage());
            return false;
        }
    }
}
