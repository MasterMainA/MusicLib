package models;

public class Album {
    private int id;
    private String title;
    private int releaseYear;
    private String artistId;
    private static int idCounter = 0;

    public Album() {}

    public Album(String title, int releaseYear, String artistId) {
        this.id = ++idCounter;
        this.title = title;
        this.releaseYear = releaseYear;
        this.artistId = normalizeArtistId(artistId);
    }

    public Album(String title, int releaseYear, int artistId) {
        this(title, releaseYear, artistId <= 0 ? "Неизвестно" : String.valueOf(artistId));
    }

    public Album(String title, int releaseYear) {
        this(title, releaseYear, "Неизвестно");
    }

    private static String normalizeArtistId(String artistId) {
        if (artistId == null || artistId.trim().isEmpty() || artistId.trim().equals("0")
                || artistId.trim().equalsIgnoreCase("неизвестно")
                || artistId.trim().equalsIgnoreCase("unknown")) {
            return "Неизвестно";
        }
        return artistId.trim();
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getReleaseYear() { return releaseYear; }
    public String getArtistId() { return artistId; }

    public void setTitle(String title) { this.title = title; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public void setArtistId(String artistId) { this.artistId = normalizeArtistId(artistId); }
    public void setArtistId(int artistId) { this.artistId = artistId <= 0 ? "Неизвестно" : String.valueOf(artistId); }

    @Override
    public String toString() {
        return "Альбом [ID: " + id + "] '" + title + "' (" + releaseYear + " год) | ID исполнителя: " + artistId;
    }
}