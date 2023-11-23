package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Address;
import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.domain.Bid;

import java.util.List;

public interface IBidRepository extends IFHBayRepository<Bid> {

    @Override
    List<Bid> getAll();

    Bid findHighestBid(Article article);

    List<Bid> getBidsOf(Article article);
}
