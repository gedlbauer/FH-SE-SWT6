package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.domain.Bid_;
import at.gedlbauer.fhbay.domain.Bid;

import javax.persistence.EntityManager;
import java.util.List;

import static at.gedlbauer.fhbay.util.JpaUtil.rollback;

public class BidRepository extends FHBayRepository<Bid> implements IBidRepository {

    public BidRepository(EntityManager em) {
        super(Bid.class, em);
    }

    @Override
    public List<Bid> getAll() {
        return em.createQuery("select b from Bid b").getResultList();
    }

    @Override
    public Bid findHighestBid(Article article) {
        try {
            return getBidsOf(article).stream().findFirst().orElse(null);
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }

    @Override
    public List<Bid> getBidsOf(Article article) {
        try {
            var cb = em.getCriteriaBuilder();
            var entryCQ = cb.createQuery(Bid.class);

            var entry = entryCQ.from(Bid.class);
            var articleParam = cb.parameter(Article.class);
            entryCQ.where(cb.equal(entry.get(Bid_.article), articleParam)).orderBy(cb.desc(entry.get(Bid_.price))).select(entry);

            var query = em.createQuery(entryCQ);
            query.setParameter(articleParam, article);
            return query.getResultList();
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }
}
