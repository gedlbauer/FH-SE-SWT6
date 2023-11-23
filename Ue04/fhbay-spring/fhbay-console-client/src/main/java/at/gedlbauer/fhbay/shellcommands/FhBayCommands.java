package at.gedlbauer.fhbay.shellcommands;

import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.domain.Bid;
import at.gedlbauer.fhbay.domain.Customer;
import at.gedlbauer.fhbay.dto.ArticleDto;
import at.gedlbauer.fhbay.dto.BidDto;
import at.gedlbauer.fhbay.dto.CustomerDto;
import at.gedlbauer.fhbay.logic.ArticleService;
import at.gedlbauer.fhbay.logic.BidService;
import at.gedlbauer.fhbay.logic.CustomerService;
import net.bytebuddy.description.method.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@ShellComponent
public class FhBayCommands {

    CustomerDto loggedInCustomer = null;

    @Autowired
    private RestTemplate template;

    // region customers
    @ShellMethod("find all customers")
    public String getCustomers() {
        return Arrays.toString(template.getForObject("http://localhost:8080/customers/all", CustomerDto[].class));
    }

    @ShellMethod("find customer by id")
    public String customerById(@ShellOption Long id) {
        var customer = template.getForObject("http://localhost:8080/customers/" + id, CustomerDto.class);
        return customer != null ? customer.toString() : "<null>";
    }

    @ShellMethod("select user")
    public String setUser(@ShellOption Long id) {
        var customer = template.getForObject("http://localhost:8080/customers/" + id, CustomerDto.class);
        if (customer == null) {
            return "Invalid user!";
        }
        loggedInCustomer = customer;
        return "Logged in as " + loggedInCustomer.getFirstName() + " " + loggedInCustomer.getLastName() + " (" +
                customer.toString() + ")";
    }

    @ShellMethod("get current user")
    public String getUser() {
        return loggedInCustomer != null ? loggedInCustomer.toString() : "<null>";
    }

    // endregion

    //region articles
    @ShellMethod("insert or update article")
    public String saveArticle(@ShellOption String name,
                              @ShellOption String description,
                              @ShellOption(defaultValue = "0.0") double startPrice,
                              @ShellOption(defaultValue = "") String auctionEnd) {
        String warning = "";
        LocalDateTime endDate = LocalDateTime.now().plusDays(7);
        if (!auctionEnd.isEmpty()) {
            try {
                endDate = LocalDateTime.parse(auctionEnd, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException ignored) {
                throw new IllegalStateException("invalid end date format - enter valid auctionEnd or leave empty to use current date + 7 days");
            }
        }
        var article = new ArticleDto(name, description, startPrice, 0.0, LocalDateTime.now(), endDate, loggedInCustomer, null);
        article = template.postForObject("http://localhost:8080/articles", article, ArticleDto.class);
        return "Inserted " + article.toString();
    }

    @ShellMethod("find all articles")
    public String getArticles() {
        return Arrays.toString(template.getForObject("http://localhost:8080/articles/all", ArticleDto[].class));
    }

    @ShellMethod("update seller of article")
    public String updateSeller(@ShellOption Long articleId, @ShellOption Long sellerId) {
        var newSeller = template.getForObject("http://localhost:8080/customers/" + sellerId, CustomerDto.class);
        var article = template.getForObject("http://localhost:8080/articles/" + articleId, ArticleDto.class);
        article.setSeller(newSeller);
        article = template.postForObject("http://localhost:8080/articles/updateSeller", article, ArticleDto.class);
        return "Updated article - new article is: " + article.toString();
    }

    @ShellMethod("update buyer of article")
    public String updateBuyer(@ShellOption Long articleId, @ShellOption Long buyerId) {
        var newBuyer = template.getForObject("http://localhost:8080/customers/" + buyerId, CustomerDto.class);
        var article = template.getForObject("http://localhost:8080/articles/" + articleId, ArticleDto.class);
        article.setBuyer(newBuyer);
        article = template.postForObject("http://localhost:8080/articles/updateBuyer", article, ArticleDto.class);
        return "Updated article - new article is: " + article.toString();
    }


    @ShellMethod("get articles where name contains")
    public String getByName(@ShellOption String name) {
        return Arrays.toString(template.getForObject("http://localhost:8080/articles/nameContaining/"+name, ArticleDto[].class));
    }

    @ShellMethod("get articles where description contains")
    public String getByDescription(@ShellOption String description) {
        return Arrays.toString(template.getForObject("http://localhost:8080/articles/descriptionContaining/"+description, ArticleDto[].class));
    }
    //endregion

    //region bids
    @ShellMethod("add bid to article")
    public String addBid(Long articleId, double price) {
        var article = template.getForObject("http://localhost:8080/articles/" + articleId, ArticleDto.class);
        var bid = new BidDto(price, LocalDateTime.now(), loggedInCustomer, article);
        bid = template.postForObject("http://localhost:8080/bids/", bid, BidDto.class);
        return "Inserted " + bid.toString();
    }
    //endregion
}
