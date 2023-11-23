package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.domain.Article_;
import at.gedlbauer.fhbay.domain.Bid;
import at.gedlbauer.fhbay.domain.Bid_;

import javax.persistence.EntityManager;
import java.util.List;

import static at.gedlbauer.fhbay.util.JpaUtil.*;

public class ArticleRepository extends FHBayRepository<Article> implements IArticleRepository {

    public ArticleRepository(EntityManager em) {
        super(Article.class, em);
    }

    @Override
    public List<Article> getAll() {
        return em.createQuery("select a from Article a").getResultList();
    }

    @Override
    public List<Article> findByName(String name) {
        try {
            var cb = em.getCriteriaBuilder();
            var entryCQ = cb.createQuery(Article.class);

            var entry = entryCQ.from(Article.class);
            var nameParam = cb.parameter(String.class);
            entryCQ.where(cb.equal(entry.get(Article_.name), nameParam)).select(entry);

            var query = em.createQuery(entryCQ);
            query.setParameter(nameParam, name);
            return query.getResultList();
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }

    @Override
    public double findPrice(Article article) {
        try {
            var cb = em.getCriteriaBuilder();
            var entryCQ = cb.createQuery(Bid.class);

            var entry = entryCQ.from(Bid.class);
            var articleParam = cb.parameter(Article.class);
            entryCQ.where(cb.equal(entry.get(Bid_.article), articleParam)).orderBy(cb.desc(entry.get(Bid_.price))).select(entry);

            var query = em.createQuery(entryCQ);
            query.setParameter(articleParam, article);
            var results = query.getResultList();
            if (results.size() == 0) {
                return 0.0;
            }
            if (results.size() == 1) {
                return results.stream().findFirst().map(x -> x.getPrice()).get();
            }
            return results.stream().skip(1).findFirst().map(x -> x.getPrice()).get();
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }
}
