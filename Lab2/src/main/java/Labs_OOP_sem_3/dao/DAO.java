package Labs_OOP_sem_3.dao;

public interface DAO<T> {
    T create(T entity);

    T read(String query);

    T update(T entity);

    void delete(T entity);
}
