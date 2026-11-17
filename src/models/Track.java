package models;

public class Track {
    private int id;
    private String title;
    private int durationSeconds;
    private String albumId; // Связь с Альбомом
    private static int idCounter = 0;

    public Track() {}

    public Track(String title, int durationSeconds, String albumId) {
        this.id = ++idCounter;
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.albumId = normalizeAlbumId(albumId);
    }

    public Track(String title, int durationSeconds, int albumId) {
        this(title, durationSeconds, albumId <= 0 ? "Сингл" : String.valueOf(albumId));
    }

    public Track(String title, int durationSeconds) {
        this(title, durationSeconds, "Сингл");
    }

    private static String normalizeAlbumId(String albumId) {
        if (albumId == null || albumId.trim().isEmpty() || albumId.trim().equals("0")
                || albumId.trim().equalsIgnoreCase("сингл")
                || albumId.trim().equalsIgnoreCase("single")) {
            return "Сингл";
        }
        return albumId.trim();
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getDurationSeconds() { return durationSeconds; }
    public String getAlbumId() { return albumId; }

    public void setTitle(String title) { this.title = title; }
    public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }
    public void setAlbumId(String albumId) { this.albumId = normalizeAlbumId(albumId); }
    public void setAlbumId(int albumId) { this.albumId = albumId <= 0 ? "Сингл" : String.valueOf(albumId); }

    @Override
    public String toString() {
        int mins = durationSeconds / 60;
        int secs = durationSeconds % 60;
        return "Трек [ID: " + id + "] '" + title + "' (" + mins + ":" + String.format("%02d", secs) + ") | ID альбома: " + albumId;
    }
}