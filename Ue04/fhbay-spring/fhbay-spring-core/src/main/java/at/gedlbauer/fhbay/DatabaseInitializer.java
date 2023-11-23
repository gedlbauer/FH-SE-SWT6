package at.gedlbauer.fhbay;

import at.gedlbauer.fhbay.dal.ArticleRepository;
import at.gedlbauer.fhbay.dal.CustomerRepository;
import at.gedlbauer.fhbay.domain.Address;
import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
@Order(-1)
@Transactional
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    Customer sandra, michael, max;
    Article smartphone, tv;

    @Override
    public void run(String... args) throws Exception {
        seedCustomers();
        seedArticles();
    }

    private void seedArticles() {
        smartphone = new Article(
                "Smartphone",
                "Fast wie neu",
                10.0,
                0.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                max,
                null);
        tv = new Article(
                "TV",
                "Fernseher",
                10.0,
                0.0,
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 7, 23, 59),
                sandra,
                null);
        smartphone = articleRepository.save(smartphone);
        tv = articleRepository.save(tv);
    }

    private void seedCustomers() {
        sandra = new Customer(
                "Sandra",
                "Steiner",
                "sandrast@trashmail.com",
                new Address("Hauptplatz 3", "4020", "Linz"),
                new Address("Hauptplatz 4", "4020", "Linz")
        );
        michael = new Customer(
                "Michael",
                "Huber",
                "michi.hubsi@aon.at",
                new Address("Hauptstraße 2", "4600", "Wels"),
                new Address("Hauptstraße 3", "4600", "Wels")
        );
        max = new Customer(
                "Max",
                "Mustermann",
                "max@mustermann.at",
                new Address("Musterstrasse 1", "1111", "Musterstadt"),
                new Address("Musterstrasse 2", "1111", "Musterstadt")
        );
        sandra = customerRepository.save(sandra);
        michael = customerRepository.save(michael);
        max = customerRepository.save(max);
    }
}
