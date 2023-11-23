package at.gedlbauer.fhbay.tests;

import at.gedlbauer.fhbay.dal.CategoryRepository;
import at.gedlbauer.fhbay.dal.ICategoryRepository;
import at.gedlbauer.fhbay.domain.Category;
import at.gedlbauer.fhbay.util.JpaUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTests {
    private ICategoryRepository categories;
    @BeforeEach
    public void initRepo() {
        JpaUtil.setPersistanceUnitName("FHBayTestPU");
        categories = new CategoryRepository(JpaUtil.getEntityManager());
    }

    @Test
    public void insertCategory() {
        Category toInsert1 = new Category("parent", null);
        Category toInsert2 = new Category("child", toInsert1);
        var tx = JpaUtil.getActiveTransaction();
        categories.insert(toInsert1);
        categories.insert(toInsert2);
        assertNotNull(toInsert1.getId());
        assertNotNull(toInsert2.getId());
        assertSame(toInsert1, JpaUtil.getEntityManager().find(Category.class, toInsert1.getId()));
        assertSame(toInsert2, JpaUtil.getEntityManager().find(Category.class, toInsert2.getId()));
        tx.rollback();
    }

    @Test
    public void removeCategory(){
        Category subject = new Category("test", null);
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(Category.class, subject.getId()));
        categories.delete(subject);
        assertNull(JpaUtil.getEntityManager().find(Category.class, subject.getId()));
        tx.rollback();
    }

    @Test
    public void updateCategory(){
        Category subject = new Category("test", null);
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(Category.class, subject.getId()));
        subject.setName("updated");
        var idBeforeUpdate = subject.getId();
        categories.updateOrInsert(subject);
        assertSame(subject, JpaUtil.getEntityManager().find(Category.class, subject.getId()));
        assertSame(idBeforeUpdate, subject.getId());
        tx.rollback();
    }

    @Test
    public void getCategoryById(){
        Category subject = new Category("test", null);
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertSame(subject, categories.findById(subject.getId()));
        tx.rollback();
    }

    @Test
    public void getAllCategories(){
        Category subject1 = new Category("test1", null);
        Category subject2 = new Category("test2", subject1);
        var expected = new ArrayList<Category>();
        expected.add(subject1);
        expected.add(subject2);
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject1);
        JpaUtil.getEntityManager().persist(subject2);
        assertArrayEquals(expected.toArray(), categories.getAll().toArray());
        tx.rollback();
    }
}
