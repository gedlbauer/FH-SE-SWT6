package swt6.spring.worklog.dao;

import swt6.spring.worklog.domain.Employee;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T, IdType extends Serializable> {
    Optional<T> findById(IdType id);

    List<T> findAll();

    void insert(T entity);

    T merge(T entity);
}
