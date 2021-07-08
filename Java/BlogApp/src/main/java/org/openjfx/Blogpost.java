package org.openjfx;

public class Blogpost {
    private static int currentId = -1;
    private final int id;
    private String author;
    private String category;
    private String title;
    private String text;
    private String date;
    private String time;
    private String imageUrl;
    private boolean published;

    public Blogpost(int id, String author, String title, String date, boolean published) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.date = date;
        this.published = published;
    }

    public Blogpost(int id, String category, String title, String text, String imageUrl, boolean published) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.text = text;
        this.imageUrl = imageUrl;
        this.published = published;
    }

    public Blogpost(int id, String author, String category, String title, String text, String date, String time, String imageUrl, boolean published) {
        this.id = id;
        this.author = author;
        this.category = category;
        this.title = title;
        this.text = text;
        this.date = date;
        this.time = time;
        this.imageUrl = imageUrl;
        this.published = published;
    }

    public static int getCurrentId() {
        return currentId;
    }

    public static void setCurrentId(int currentId) {
        Blogpost.currentId = currentId;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublished() {
        return published ? "Öffentlich" : "Entwurf";
    }

    public boolean getPublishedBool() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String toString() {
        return (this.id + '\n' +
                "Author: " + this.author + '\n' +
                "Kategorie: " + this.category + '\n' +
                "Titel: " + this.title + '\n' +
                "Textersatz" + '\n' +
                "Datum: " + this.date + '\n' +
                "Zeit: " + this.time + '\n' +
                "Bild URL: " + this.imageUrl + '\n' +
                "Veröffentlicht: " + this.published);
    }
}
