package repositories;

import models.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistRepository implements RepositoryInterface<Artist> {
    private final List<Artist> database = new ArrayList<>();

    public ArtistRepository() {
        generateDataBase();
    }

    private void generateDataBase() {
        String[] names = {"Queen", "Ed Sheeran", "The Weeknd", "Nirvana", "Daft Punk", "Adele", "Eminem", "Metallica", "Coldplay", "Lady Gaga"};
        String[] genres = {"Rock", "Pop", "R&B", "Grunge", "Electronic", "Pop", "Hip Hop", "Metal", "Alternative", "Pop"};
        String[] country = {"UK", "UK", "Canada", "USA", "France", "UK", "USA", "USA", "UK", "USA"};
        for (int i = 0; i < 10; i++) {
            database.add(new Artist(names[i], genres[i], country[i]));
        }
    }

    @Override
    public void create(Artist item) {
        database.add(item);
    }

    @Override
    public Artist getById(int id) {
        return database.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Artist> getAll() {
        return new ArrayList<>(database);
    }

    @Override
    public void update(int id, Artist item) {
        Artist existing = getById(id);
        if (existing != null) {
            existing.setName(item.getName());
            existing.setGenre(item.getGenre());
            existing.setCountry(item.getCountry());
        }
    }

    @Override
    public void delete(int id) {
        database.removeIf(a -> a.getId() == id);
    }
}
