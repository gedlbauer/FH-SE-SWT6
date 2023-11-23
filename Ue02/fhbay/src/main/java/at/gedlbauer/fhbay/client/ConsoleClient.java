package at.gedlbauer.fhbay.client;

import at.gedlbauer.fhbay.dal.*;
import at.gedlbauer.fhbay.domain.*;
import at.gedlbauer.fhbay.util.JpaUtil;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.logging.Level;

public class ConsoleClient {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        var logger = LoggerFactory.getLogger(ConsoleClient.class);
        logger.debug("setting up db");
        JpaUtil.setPersistanceUnitName("FHBayConsolePU");
        var em = JpaUtil.getEntityManager();
        logger.debug("setup complete");


        // region customers
        logger.info("inserting customers");
        logger.debug("transaction start");
        var tx = JpaUtil.getActiveTransaction();
        ICustomerRepository customerRepo = new CustomerRepository(em);
        Customer max = new Customer(
                "Max",
                "Mustermann",
                "max@mustermann.at",
                new Address("Musterstrasse 1", "1111", "Musterstadt")
        );
        customerRepo.insert(max);

        Customer pauli = new Customer(
                "Pauline",
                "Musterfrau",
                "pauli@musterfrau.at",
                new Address("Musterstrasse 2", "2222", "Musterdorf")
        );
        customerRepo.insert(pauli);

        Customer sandra = new Customer(
                "Sandra",
                "Steiner",
                "sandrast@trashmail.com",
                new Address("Hauptplatz 3", "4020", "Linz")
        );
        customerRepo.insert(sandra);

        Customer michael = new Customer(
                "Michael",
                "Huber",
                "michi.hubsi@aon.at",
                new Address("Hauptstraße 2", "4600", "Wels")
        );
        customerRepo.insert(michael);
        tx.commit();
        logger.debug("transaction end");
        logger.info("inserted customers");
        //endregion

        // region articles
        IArticleRepository articleRepo = new ArticleRepository(em);
        logger.info("inserting articles");
        logger.debug("transaction start");
        tx = JpaUtil.getActiveTransaction();
        Article smartphone = new Article(
                "Smartphone",
                "Fast wie neu",
                10.0,
                0.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                max,
                null,
                new Category("Telefone", null));
        Article tv = new Article(
                "TV",
                "Fernseher",
                10.0,
                0.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                pauli,
                null,
                new Category("Fernseher", null));
        articleRepo.insert(smartphone);
        articleRepo.insert(tv);
        tx.commit();
        logger.debug("transaction end");
        logger.info("inserted articles");
        // endregion

        // region bids
        logger.info("inserting bids");
        IBidRepository bidRepository = new BidRepository(em);

        logger.debug("transaction start");
        tx = JpaUtil.getActiveTransaction();
        bidRepository.insert(new Bid(30.0, LocalDateTime.of(2022, 1, 1, 14, 0), sandra, smartphone));
        tx.commit();
        logger.debug("transaction end");

        logger.debug("transaction start");
        tx = JpaUtil.getActiveTransaction();
        bidRepository.insert(new Bid(40.0, LocalDateTime.of(2022, 1, 2, 14, 0), michael, smartphone));
        tx.commit();
        logger.debug("transaction end");

        logger.debug("transaction start");
        tx = JpaUtil.getActiveTransaction();
        bidRepository.insert(new Bid(50.0, LocalDateTime.of(2022, 1, 3, 14, 0), pauli, smartphone));
        tx.commit();
        logger.debug("transaction end");

        logger.debug("transaction start");
        tx = JpaUtil.getActiveTransaction();
        bidRepository.insert(new Bid(50.0, LocalDateTime.of(2022, 1, 4, 14, 0), max, tv));
        tx.commit();
        logger.debug("transaction end");

        logger.debug("transaction start");
        tx = JpaUtil.getActiveTransaction();
        bidRepository.insert(new Bid(70.0, LocalDateTime.of(2022, 1, 5, 14, 0), michael, tv));
        tx.commit();
        logger.debug("transaction end");

        logger.debug("transaction start");
        tx = JpaUtil.getActiveTransaction();
        bidRepository.insert(new Bid(80.0, LocalDateTime.of(2022, 1, 6, 14, 0), sandra, tv));
        tx.commit();
        logger.debug("transaction end");
        logger.info("inserted bids");
        // endregion

        // region auction expiration

        logger.info("simulating auction end of tv");
        logger.debug("transaction start");
        tx = JpaUtil.getActiveTransaction();
        tv.setBuyer(bidRepository.findHighestBid(tv).getBidder());
        tv.setEndPrice(articleRepo.findPrice(tv));
        articleRepo.updateOrInsert(tv);
        tx.commit();
        logger.debug("transaction end");
        // endregion
    }
}
