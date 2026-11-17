package repositories;

import java.util.List;

public interface RepositoryInterface<T> {
    void create(T item);
    T getById(int id);
    List<T> getAll();
    void update(int id, T item);
    void delete(int id);
}
