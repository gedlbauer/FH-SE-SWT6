package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Category;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoryRepository extends FHBayRepository<Category> implements ICategoryRepository {

    public CategoryRepository(EntityManager em) {
        super(Category.class, em);
    }

    @Override
    public List<Category> getAll() {
        return em.createQuery("select c from Category c").getResultList();
    }
}
