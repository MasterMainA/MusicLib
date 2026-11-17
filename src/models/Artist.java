package models;

public class Artist {
    private String name;
    private String genre;
    private String country;
    private int id;

    private static int idCounter = 0;

    public Artist() {}

    public Artist(String name, String genre, String country) {
        this.name = name;
        this.genre = genre;
        this.country = country;
        this.id = ++idCounter;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountry() {
        return country;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name; }

    public void setGenre(String genre) {
        this.genre = genre; }

    public void setCountry(String country) {
        this.country = country;}

    @Override
    public String toString() {
        return "Исполнитель [ID: " + id + "] " + name + " (Жанр: " + genre + ")" + " Страна: " + country;   }
}