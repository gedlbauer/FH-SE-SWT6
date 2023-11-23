package at.gedlbauer.fhbay.tests;

import at.gedlbauer.fhbay.dal.ArticleRepository;
import at.gedlbauer.fhbay.dal.IArticleRepository;
import at.gedlbauer.fhbay.domain.*;
import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.util.JpaUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ArticleTests {
    private IArticleRepository articles;

    @BeforeEach
    public void initRepo() {
        JpaUtil.setPersistanceUnitName("FHBayTestPU");
        articles = new ArticleRepository(JpaUtil.getEntityManager());
    }

    @Test
    public void insertArticle() {
        Article toInsert1 = new Article(
                "Smartphone",
                "Schlaues Telefon",
                10.0,
                100.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        var tx = JpaUtil.getActiveTransaction();
        articles.insert(toInsert1);
        assertNotNull(toInsert1.getId());
        assertSame(toInsert1, JpaUtil.getEntityManager().find(Article.class, toInsert1.getId()));
        tx.rollback();
    }

    @Test
    public void removeArticle() {
        Article subject = new Article(
                "Smartphone",
                "Schlaues Telefon",
                10.0,
                100.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(Article.class, subject.getId()));
        articles.delete(subject);
        assertNull(JpaUtil.getEntityManager().find(Article.class, subject.getId()));
        tx.rollback();
    }

    @Test
    public void updateArticle() {
        Article subject = new Article(
                "Smartphone",
                "Schlaues Telefon",
                10.0,
                100.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(Article.class, subject.getId()));
        subject.setName("updated");
        var idBeforeUpdate = subject.getId();
        articles.updateOrInsert(subject);
        assertSame(subject, JpaUtil.getEntityManager().find(Article.class, subject.getId()));
        assertSame(idBeforeUpdate, subject.getId());
        tx.rollback();
    }

    @Test
    public void getArticleById() {
        Article subject = new Article(
                "Smartphone",
                "Schlaues Telefon",
                10.0,
                100.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertSame(subject, articles.findById(subject.getId()));
        tx.rollback();
    }

    @Test
    public void getAllArticles() {
        Article subject1 = new Article(
                "Smartphone",
                "Schlaues Telefon",
                10.0,
                100.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        Article subject2 = new Article(
                "TV",
                "Fernseher",
                10.0,
                100.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        var expected = new ArrayList<Article>();
        expected.add(subject1);
        expected.add(subject2);
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject1);
        JpaUtil.getEntityManager().persist(subject2);
        JpaUtil.getEntityManager().flush();
        assertArrayEquals(expected.toArray(), articles.getAll().toArray());
        tx.rollback();
    }

    @Test
    public void findArticleByName() {
        Article subject1 = new Article(
                "Smartphone",
                "Schlaues Telefon",
                10.0,
                100.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        Article subject2 = new Article(
                "TV",
                "Fernseher",
                10.0,
                100.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        Article subject3 = new Article(
                "Smartphone",
                "Telefon 2",
                20.0,
                200.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Mike", "Mustermann", "mike.mustermann", new Address("", "", "")),
                new Customer("Stefanie", "Musterfrau", "stefanie.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        var expected = new ArrayList<Article>();
        expected.add(subject1);
        expected.add(subject3);
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject1);
        JpaUtil.getEntityManager().persist(subject2);
        JpaUtil.getEntityManager().persist(subject3);
        JpaUtil.getEntityManager().flush();
        assertArrayEquals(expected.toArray(), articles.findByName("Smartphone").toArray());
        tx.rollback();
    }

    @Test
    public void findPrice() {
        var tx = JpaUtil.getActiveTransaction();
        Article subject = new Article(
                "Smartphone",
                "Schlaues Telefon",
                10.0,
                100.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        JpaUtil.getEntityManager().persist(subject);
        Bid bid1 = new Bid(
                10.0, LocalDateTime.of(2022, 1, 1, 1, 0),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                subject
        );
        Bid bid2 = new Bid(
                20.0, LocalDateTime.of(2022, 1, 1, 2, 0),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                subject
        );
        Bid bid3 = new Bid(
                30.0, LocalDateTime.of(2022, 1, 1, 3, 0),
                new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                subject
        );
        JpaUtil.getEntityManager().persist(bid1);
        JpaUtil.getEntityManager().persist(bid2);
        JpaUtil.getEntityManager().persist(bid3);
        assertEquals(20.0, articles.findPrice(subject), 0.001);
        tx.rollback();
    }
}
