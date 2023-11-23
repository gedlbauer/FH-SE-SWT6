package at.gedlbauer.fhbay.dal;

import java.util.List;

public interface IFHBayRepository<T> {
    void insert(T entity);

    T findById(Long id);

    List<T> getAll();

    T updateOrInsert(T entity);

    void delete(T entity);
}
