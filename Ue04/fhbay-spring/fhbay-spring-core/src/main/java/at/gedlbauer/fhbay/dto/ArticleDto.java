package at.gedlbauer.fhbay.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonAutoDetect
public class ArticleDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private double startPrice;
    private double endPrice;
    private LocalDateTime auctionStart;
    private LocalDateTime auctionEnd;

    private CustomerDto seller;
    private CustomerDto buyer;

    public ArticleDto() {
    }

    public ArticleDto(String name, String description, double startPrice, double endPrice, LocalDateTime auctionStart, LocalDateTime auctionEnd, CustomerDto seller, CustomerDto buyer) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.auctionStart = auctionStart;
        this.auctionEnd = auctionEnd;
        this.seller = seller;
        this.buyer = buyer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
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

    public CustomerDto getSeller() {
        return seller;
    }

    public void setSeller(CustomerDto seller) {
        this.seller = seller;
    }

    public CustomerDto getBuyer() {
        return buyer;
    }

    public void setBuyer(CustomerDto buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ArticleDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", startPrice=").append(startPrice);
        sb.append(", endPrice=").append(endPrice);
        sb.append(", auctionStart=").append(auctionStart);
        sb.append(", auctionEnd=").append(auctionEnd);
        sb.append(", seller=").append(seller);
        sb.append(", buyer=").append(buyer);
        sb.append('}');
        return sb.toString();
    }
}
