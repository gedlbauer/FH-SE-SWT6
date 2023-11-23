package at.gedlbauer.fhbay.dal;

import static at.gedlbauer.fhbay.util.JpaUtil.*;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class FHBayRepository<T> implements IFHBayRepository<T> {
    protected EntityManager em;
    private Class<T> tClass;

    public FHBayRepository(Class<T> tClass, EntityManager em) {
        this.tClass = tClass;
        this.em = em;
    }

    @Override
    public void insert(T entity) {
        try {
            em.persist(entity);
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }

    @Override
    public T findById(Long id) {
        return em.find(tClass, id);
    }

    @Override
    public T updateOrInsert(T entity) {
        try {
            return em.merge(entity);
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }

    @Override
    public void delete(T entity) {
        try {
            em.remove(entity);
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }
}
