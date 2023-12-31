package at.gedlbauer.fhbay.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Article {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private double startPrice;
    private double endPrice;
    private LocalDateTime auctionStart;
    private LocalDateTime auctionEnd;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST}) // EAGER, weil Verkäufer quasi immer gebraucht wird
    @Fetch(FetchMode.JOIN)
    private Customer seller;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    private Customer buyer;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Set<Bid> bids = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.JOIN)
    private Category category;

    public Article(String name, String description, double startPrice, double endPrice, LocalDateTime auctionStart, LocalDateTime auctionEnd, Customer seller, Customer buyer, Category category) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.auctionStart = auctionStart;
        this.auctionEnd = auctionEnd;
        this.seller = seller;
        this.buyer = buyer;
        this.category = category;
    }

    public Article() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // region getters/setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Double endPrice) {
        this.endPrice = endPrice;
    }

    public LocalDateTime getAuctionStart() {
        return auctionStart;
    }

    public void setAuctionStart(LocalDateTime auctionStart) {
        this.auctionStart = auctionStart;
    }

    public LocalDateTime getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(LocalDateTime auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    public Customer getSeller() {
        return seller;
    }

    public void setSeller(Customer seller) {
        this.seller = seller;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }
    // endregion
}
