package services;

import models.Album;
import models.Artist;
import models.Track;
import repositories.RepositoryInterface;

import java.util.List;

public class AlbumService implements ServiceInterface<Album> {
    private final RepositoryInterface<Album> repository;
    private final RepositoryInterface<Track> trackRepository;
    private final RepositoryInterface<Artist> artistRepository;

    public AlbumService(RepositoryInterface<Album> repository) {
        this(repository, null, null);
    }

    public AlbumService(RepositoryInterface<Album> repository, RepositoryInterface<Track> trackRepository) {
        this(repository, trackRepository, null);
    }

    public AlbumService(RepositoryInterface<Album> repository, RepositoryInterface<Track> trackRepository, RepositoryInterface<Artist> artistRepository) {
        this.repository = repository;
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public void create(Album item) {
        validateArtist(item);
        repository.create(item);
    }

    @Override
    public Album getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Album> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(int id, Album item) {
        if (getById(id) != null) {
            validateArtist(item);
            repository.update(id, item);
        }
    }

    @Override
    public boolean delete(int id) {
        if (getById(id) != null) {
            if (trackRepository != null) {
                String albumIdStr = String.valueOf(id);
                for (Track track : trackRepository.getAll()) {
                    if (albumIdStr.equals(track.getAlbumId())) {
                        track.setAlbumId("Сингл");
                        trackRepository.update(track.getId(), track);
                    }
                }
            }
            repository.delete(id);
            return true;
        }
        return false;
    }

    private void validateArtist(Album item) {
        if (item == null) return;
        String artistId = item.getArtistId();
        if (artistId == null || artistId.isBlank() || artistId.equals("0")
                || artistId.equalsIgnoreCase("неизвестно") || artistId.equalsIgnoreCase("unknown")) {
            item.setArtistId("Неизвестно");
            return;
        }
        if (artistRepository != null) {
            try {
                int aId = Integer.parseInt(artistId);
                if (artistRepository.getById(aId) == null) {
                    item.setArtistId("Неизвестно");
                }
            } catch (NumberFormatException ignored) {}
        }
    }
}
