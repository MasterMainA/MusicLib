package repositories;
import models.Album;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepository implements RepositoryInterface<Album> {
    private final List<Album> database = new ArrayList<>();

    public AlbumRepository() { generateDataBase(); }

    private void generateDataBase() {
        String[] titles = {"A Night at the Opera", "Divide", "After Hours", "Nevermind", "Discovery", "21", "The Eminem Show", "Master of Puppets", "Parachutes", "The Fame"};
        int[] years = {1975, 2017, 2020, 1991, 2001, 2011, 2002, 1986, 2000, 2008};
        for (int i = 0; i < 10; i++) {
            database.add(new Album(titles[i], years[i], i + 1)); // Привязываем к Artist по ID (1-10)
        }
    }

    @Override public void create(Album item) { database.add(item); }
    @Override public Album getById(int id) { return database.stream().filter(a -> a.getId() == id).findFirst().orElse(null); }
    @Override public List<Album> getAll() { return new ArrayList<>(database); }
    @Override public void delete(int id) { database.removeIf(a -> a.getId() == id); }
    @Override public void update(int id, Album item) {
        Album current = getById(id);
        if (current != null) {
            current.setTitle(item.getTitle());
            current.setReleaseYear(item.getReleaseYear());
            current.setArtistId(item.getArtistId());
        }
    }
}