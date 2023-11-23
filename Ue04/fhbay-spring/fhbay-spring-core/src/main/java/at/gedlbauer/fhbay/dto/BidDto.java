package at.gedlbauer.fhbay.dto;

import java.time.LocalDateTime;

public class BidDto {
    private Long id;
    private double price;
    private LocalDateTime dateTime;
    private CustomerDto bidder;
    private ArticleDto article;

    public BidDto() {
    }

    public BidDto(double price, LocalDateTime dateTime, CustomerDto bidder, ArticleDto article) {
        this.price = price;
        this.dateTime = dateTime;
        this.bidder = bidder;
        this.article = article;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public CustomerDto getBidder() {
        return bidder;
    }

    public void setBidder(CustomerDto bidder) {
        this.bidder = bidder;
    }

    public ArticleDto getArticle() {
        return article;
    }

    public void setArticle(ArticleDto article) {
        this.article = article;
    }
}
