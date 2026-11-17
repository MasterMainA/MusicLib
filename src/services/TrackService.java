package services;

import models.Album;
import models.Track;
import repositories.RepositoryInterface;

import java.util.List;

public class TrackService implements ServiceInterface<Track> {
    private final RepositoryInterface<Track> repository;
    private final RepositoryInterface<Album> albumRepository;

    public TrackService(RepositoryInterface<Track> repository) {
        this(repository, null);
    }

    public TrackService(RepositoryInterface<Track> repository, RepositoryInterface<Album> albumRepository) {
        this.repository = repository;
        this.albumRepository = albumRepository;
    }

    @Override
    public void create(Track item) {
        validateAlbum(item);
        repository.create(item);
    }

    @Override
    public Track getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Track> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(int id, Track item) {
        if (repository.getById(id) != null) {
            validateAlbum(item);
            repository.update(id, item);
        }
    }

    @Override
    public boolean delete(int id) {
        if (repository.getById(id) != null) {
            repository.delete(id);
            return true;
        }
        return false;
    }

    private void validateAlbum(Track item) {
        if (item == null) return;
        String albumId = item.getAlbumId();
        if (albumId == null || albumId.isBlank() || albumId.equals("0")
                || albumId.equalsIgnoreCase("сингл") || albumId.equalsIgnoreCase("single")) {
            item.setAlbumId("Сингл");
            return;
        }
        if (albumRepository != null) {
            try {
                int aId = Integer.parseInt(albumId);
                if (albumRepository.getById(aId) == null) {
                    item.setAlbumId("Сингл");
                }
            } catch (NumberFormatException ignored) {}
        }
    }
}
