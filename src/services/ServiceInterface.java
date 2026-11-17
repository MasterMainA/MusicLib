package services;

import java.util.List;

public interface ServiceInterface<T> {
    void create(T item);
    T getById(int id);
    List<T> getAll();
    void update(int id, T item);
    boolean delete(int id);
}
