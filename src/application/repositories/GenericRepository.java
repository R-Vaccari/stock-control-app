package application.repositories;

public interface GenericRepository<T> {

    void insert(T item);
    void update(T item);
    void delete(T item);
}
