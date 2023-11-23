package at.gedlbauer.fhbay.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bid {
    @Id
    @GeneratedValue
    private Long id;

    private double price;
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Customer bidder;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Article article;

    public Bid(double price, LocalDateTime dateTime, Customer bidder, Article article) {
        this.price = price;
        this.dateTime = dateTime;
        this.bidder = bidder;
        this.article = article;
    }

    public Bid() {
    }

    // region getters/setters
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

    public Customer getBidder() {
        return bidder;
    }

    public void setBidder(Customer bidder) {
        this.bidder = bidder;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    //endregion


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bid{");
        sb.append("id=").append(id);
        sb.append(", price=").append(price);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", bidder=").append(bidder);
        sb.append('}');
        return sb.toString();
    }
}
