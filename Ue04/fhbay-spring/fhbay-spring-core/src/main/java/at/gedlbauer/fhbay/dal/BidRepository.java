package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findTop2ByArticleOrderByPriceDesc(Article article);
}
