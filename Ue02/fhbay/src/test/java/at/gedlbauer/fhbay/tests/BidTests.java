package at.gedlbauer.fhbay.tests;

import at.gedlbauer.fhbay.dal.BidRepository;
import at.gedlbauer.fhbay.dal.IBidRepository;
import at.gedlbauer.fhbay.domain.*;
import at.gedlbauer.fhbay.util.JpaUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BidTests {
    private IBidRepository bids;

    @BeforeEach
    public void initRepo() {
        JpaUtil.setPersistanceUnitName("FHBayTestPU");
        bids = new BidRepository(JpaUtil.getEntityManager());
    }

    @Test
    public void insertBid() {
        Bid toInsert = new Bid(
                10.0,
                LocalDateTime.of(2022, 1, 1, 14, 0),
                new Customer(
                        "Max",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                new Article(
                        "Smartphone",
                        "Schlaues Telefon",
                        10.0,
                        100.0,
                        LocalDateTime.of(2022, 1, 1, 0, 0),
                        LocalDateTime.of(2022, 1, 7, 23, 59),
                        new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                        new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                        new Category("xxx", null)));
        var tx = JpaUtil.getActiveTransaction();
        bids.insert(toInsert);
        assertNotNull(toInsert.getId());
        assertSame(toInsert, JpaUtil.getEntityManager().find(Bid.class, toInsert.getId()));
        tx.rollback();
    }

    @Test
    public void removeBid() {
        Bid subject = new Bid(
                10.0,
                LocalDateTime.of(2022, 1, 1, 14, 0),
                new Customer(
                        "Max",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                new Article(
                        "Smartphone",
                        "Schlaues Telefon",
                        10.0,
                        100.0,
                        LocalDateTime.of(2022, 1, 1, 0, 0),
                        LocalDateTime.of(2022, 1, 7, 23, 59),
                        new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                        new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                        new Category("xxx", null)));
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(Bid.class, subject.getId()));
        bids.delete(subject);
        assertNull(JpaUtil.getEntityManager().find(Bid.class, subject.getId()));
        tx.rollback();
    }

    @Test
    public void updateBid() {
        Bid subject = new Bid(
                10.0,
                LocalDateTime.of(2022, 1, 1, 14, 0),
                new Customer(
                        "Franz",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                new Article(
                        "Smartphone",
                        "Schlaues Telefon",
                        10.0,
                        100.0,
                        LocalDateTime.of(2022, 1, 1, 0, 0),
                        LocalDateTime.of(2022, 1, 7, 23, 59),
                        new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                        new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                        new Category("xxx", null)));
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(Bid.class, subject.getId()));
        subject.setPrice(20.0);
        var idBeforeUpdate = subject.getId();
        bids.updateOrInsert(subject);
        assertSame(subject, JpaUtil.getEntityManager().find(Bid.class, subject.getId()));
        assertSame(idBeforeUpdate, subject.getId());
        tx.rollback();
    }

    @Test
    public void getBidById() {
        Bid subject = new Bid(
                10.0,
                LocalDateTime.of(2022, 1, 1, 14, 0),
                new Customer(
                        "Franz",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                new Article(
                        "Smartphone",
                        "Schlaues Telefon",
                        10.0,
                        100.0,
                        LocalDateTime.of(2022, 1, 1, 0, 0),
                        LocalDateTime.of(2022, 1, 7, 23, 59),
                        new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                        new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                        new Category("xxx", null)));
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertSame(subject, bids.findById(subject.getId()));
        tx.rollback();
    }

    @Test
    public void getAllBids() {
        Bid subject1 = new Bid(
                30.0,
                LocalDateTime.of(2022, 1, 1, 14, 0),
                new Customer(
                        "Paul",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                new Article(
                        "Smartphone",
                        "Schlaues Telefon",
                        10.0,
                        100.0,
                        LocalDateTime.of(2022, 1, 1, 0, 0),
                        LocalDateTime.of(2022, 1, 7, 23, 59),
                        new Customer("Max", "Mustermann", "max.mustermann", new Address("", "", "")),
                        new Customer("Susi", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                        new Category("xxx", null)));
        Bid subject2 = new Bid(
                10.0,
                LocalDateTime.of(2022, 1, 1, 14, 0),
                new Customer(
                        "Peter",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                new Article(
                        "TV",
                        "Fernseher",
                        20.0,
                        400.0,
                        LocalDateTime.of(2022, 1, 1, 0, 0),
                        LocalDateTime.of(2022, 1, 7, 23, 59),
                        new Customer("Andreas", "Mustermann", "max.mustermann", new Address("", "", "")),
                        new Customer("Michaela", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                        new Category("xxx", null)));
        var expected = new ArrayList<Bid>();
        expected.add(subject1);
        expected.add(subject2);
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject1);
        JpaUtil.getEntityManager().persist(subject2);
        assertArrayEquals(expected.toArray(), bids.getAll().toArray());
        tx.rollback();
    }

    @Test
    public void findHighestBidFromArticle(){
        var article = new Article(
                "TV",
                "Fernseher",
                20.0,
                400.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Andreas", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Michaela", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        var wrongArticle = new Article(
                "Smartphone",
                "Handy",
                20.0,
                400.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                new Customer("Andreas", "Mustermann", "max.mustermann", new Address("", "", "")),
                new Customer("Michaela", "Musterfrau", "susi.musterfrau", new Address("", "", "")),
                new Category("xxx", null));
        var customer1 =  new Customer(
                "Peter",
                "Mustermann",
                "max@mustermann.at",
                new Address("Musterstrasse 1", "1111", "Musterstadt"));
        var customer2 = new Customer(
                "Paul",
                "Mustermann",
                "paul@mustermann.at",
                new Address("Musterstrasse 2", "1111", "Musterstadt"));

        var bid1 = new Bid(10.0, LocalDateTime.of(2022, 1, 1, 12, 0), customer1, article);
        var bid2 = new Bid(20.0, LocalDateTime.of(2022, 1, 1, 13, 0), customer2, article);
        var bid3 = new Bid(30.0, LocalDateTime.of(2022, 1, 1, 14, 0), customer1, article);
        var bid4 = new Bid(40.0, LocalDateTime.of(2022, 1, 1, 15, 0), customer2, article);
        var bid5 = new Bid(50.0, LocalDateTime.of(2022, 1, 1, 16, 0), customer1, article);
        var invalidBid = new Bid(60.0, LocalDateTime.of(2022, 1, 1, 16, 0), customer1, wrongArticle);

        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(bid1);
        JpaUtil.getEntityManager().persist(bid2);
        JpaUtil.getEntityManager().persist(bid3);
        JpaUtil.getEntityManager().persist(bid4);
        JpaUtil.getEntityManager().persist(bid5);
        JpaUtil.getEntityManager().persist(invalidBid);
        assertEquals(bid5, bids.findHighestBid(article));
        tx.rollback();
    }

    @Test
    public void findHighestBid() {
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
        assertSame(bid3, bids.findHighestBid(subject));
        tx.rollback();
    }

    @Test
    public void getBidsOf() {
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
        List<Bid> expected = new ArrayList<>();
        expected.add(bid3);
        expected.add(bid2);
        expected.add(bid1);
        assertArrayEquals(expected.toArray(), bids.getBidsOf(subject).toArray());
        tx.rollback();
    }
}
