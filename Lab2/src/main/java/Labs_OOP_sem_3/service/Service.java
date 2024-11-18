package Labs_OOP_sem_3.service;

public interface Service<T> {
    T create(T entity);

    T read(String query);

    T update(T entity);

    void delete(T entity);
}
