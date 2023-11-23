package at.gedlbauer.fhbay.logic.impl;

import at.gedlbauer.fhbay.dal.ArticleRepository;
import at.gedlbauer.fhbay.dal.BidRepository;
import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.domain.Customer;
import at.gedlbauer.fhbay.logic.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private TaskScheduler auctionScheduler;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private BidRepository bidRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article save(Article article) {
        if (article.getId() != null) {
            throw new IllegalStateException("id must not be set");
        }
        if (article.getSeller() == null) {
            throw new IllegalStateException("article.seller must not be null!");
        }
        final Article insertedArticle = articleRepository.save(article);
        Date auctionEnd = Date.from(article.getAuctionEnd().atZone(ZoneId.systemDefault()).toInstant());
        auctionScheduler.schedule(() -> endAuction(insertedArticle), auctionEnd);
        return insertedArticle;
    }

    @Override
    public Article updateSeller(Article article, Customer newSeller) {
        if (newSeller == null) {
            throw new IllegalStateException("article.seller must not be null!");
        }
        article.setSeller(newSeller);
        return articleRepository.save(article);
    }

    @Override
    public Article updateBuyer(Article article, Customer newBuyer) {
        article.setBuyer(newBuyer);
        return articleRepository.save(article);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findByName(String name) {
        return articleRepository.findByNameContaining(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findByDescription(String description) {
        return articleRepository.findByDescriptionContaining(description);
    }

    @Override
    public Article endAuction(Article article) {
        var topBids = bidRepository.findTop2ByArticleOrderByPriceDesc(article);
        if (topBids.isEmpty()) {
            article.setEndPrice(0.0);
            article.setBuyer(article.getSeller());
        } else if (topBids.size() == 1) {
            var topBid = topBids.get(0);
            article.setEndPrice(topBid.getPrice());
            article.setBuyer(topBid.getBidder());
        } else {
            article.setEndPrice(topBids.get(1).getPrice());
            article.setBuyer(topBids.get(0).getBidder());
        }
        return articleRepository.save(article);
    }

}
