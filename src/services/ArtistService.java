package services;

import models.Album;
import models.Artist;
import repositories.RepositoryInterface;

import java.util.List;

public class ArtistService implements ServiceInterface<Artist> {
    private final RepositoryInterface<Artist> repository;
    private final RepositoryInterface<Album> albumRepository;

    public ArtistService(RepositoryInterface<Artist> repository) {
        this(repository, null);
    }

    public ArtistService(RepositoryInterface<Artist> repository, RepositoryInterface<Album> albumRepository) {
        this.repository = repository;
        this.albumRepository = albumRepository;
    }

    @Override
    public void create(Artist item) {
        repository.create(item);
    }

    @Override
    public Artist getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Artist> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(int id, Artist item) {
        if (getById(id) != null) repository.update(id, item);
    }

    @Override
    public boolean delete(int id) {
        if (getById(id) != null) {
            if (albumRepository != null) {
                String artistIdStr = String.valueOf(id);
                for (Album album : albumRepository.getAll()) {
                    if (artistIdStr.equals(album.getArtistId())) {
                        album.setArtistId("Неизвестно");
                        albumRepository.update(album.getId(), album);
                    }
                }
            }
            repository.delete(id);
            return true;
        }
        return false;
    }
}