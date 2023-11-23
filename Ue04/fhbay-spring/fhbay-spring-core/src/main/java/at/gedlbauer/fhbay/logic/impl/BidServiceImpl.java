package at.gedlbauer.fhbay.logic.impl;

import at.gedlbauer.fhbay.dal.BidRepository;
import at.gedlbauer.fhbay.domain.Bid;
import at.gedlbauer.fhbay.logic.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("bidService")
@Transactional
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;

    @Override
    public Bid save(Bid bid) {
        var article = bid.getArticle();
        if (bid.getBidder() == null) {
            throw new IllegalStateException("bid.bidder must not be null!");
        }
        if(article == null){
            throw new IllegalStateException("bid.article must not be null");
        }
        if (bid.getDateTime().isBefore(article.getAuctionStart()) ||
                bid.getDateTime().isAfter(article.getAuctionEnd())) {
            throw new IllegalStateException("cannot bid on article if auction is not running");
        }
        article.getBids().add(bid);
        return bidRepository.save(bid);
    }
}
