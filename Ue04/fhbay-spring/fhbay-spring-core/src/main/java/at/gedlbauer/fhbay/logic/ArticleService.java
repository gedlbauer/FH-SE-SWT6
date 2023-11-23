package at.gedlbauer.fhbay.logic;

import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.domain.Bid;
import at.gedlbauer.fhbay.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> findAll();

    Optional<Article> findById(Long id);

    Article save(Article article);

    Article updateSeller(Article article, Customer newSeller);

    Article updateBuyer(Article article, Customer newBuyer);

    List<Article> findByName(String name);

    List<Article> findByDescription(String description);

    Article endAuction(Article article);
}
