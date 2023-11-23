package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Address;
import at.gedlbauer.fhbay.domain.Article;

import java.util.List;

public interface IArticleRepository extends IFHBayRepository<Article> {

    @Override
    List<Article> getAll();

    List<Article> findByName(String name);

    double findPrice(Article article);
}
