package repositories;

import models.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackRepository implements RepositoryInterface<Track> {
    private final List<Track> database = new ArrayList<>();

    public TrackRepository() {
        generateDataBase();
    }

    private void generateDataBase() {
        String[] titles = {"Bohemian Rhapsody", "Shape of You", "Blinding Lights", "Smells Like Teen Spirit", "Harder, Better, Faster, Stronger", "Rolling in the Deep", "Without Me", "Battery", "Yellow", "Poker Face"};
        int[] durations = {354, 233, 200, 301, 224, 228, 290, 312, 269, 237};

        int n = titles.length;
        for (int i = 0; i < n; i++) {
            database.add(new Track(titles[i], durations[i], i + 1));
        }
    }

    @Override
    public void create(Track item) {
        database.add(item);
    }

    @Override
    public Track getById(int id) {
        return database.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Track> getAll() {
        return new ArrayList<>(database);
    }

    @Override
    public void update(int id, Track item) {
        Track current = getById(id);
        if (current != null) {
            current.setTitle(item.getTitle());
            current.setDurationSeconds(item.getDurationSeconds());
            current.setAlbumId(item.getAlbumId());
        }
    }

    @Override
    public void delete(int id) {
        database.removeIf(m -> m.getId() == id);
    }
}
