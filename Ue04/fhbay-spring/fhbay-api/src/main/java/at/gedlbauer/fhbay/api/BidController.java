package at.gedlbauer.fhbay.api;


import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.domain.Bid;
import at.gedlbauer.fhbay.dto.ArticleDto;
import at.gedlbauer.fhbay.dto.BidDto;
import at.gedlbauer.fhbay.logic.ArticleService;
import at.gedlbauer.fhbay.logic.BidService;
import at.gedlbauer.fhbay.logic.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bids", produces = MediaType.APPLICATION_JSON_VALUE)
public class BidController {

    @Autowired
    private BidService bidService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping(value = "")
    public BidDto insertBid(@RequestBody BidDto bidDto) {
        var bid = mapper.map(bidDto, Bid.class);
        bid.setArticle(articleService.findById(bidDto.getArticle().getId()).orElseThrow());
        bid.setBidder(customerService.findById(bidDto.getBidder().getId()).orElseThrow());
        bid = bidService.save(bid);
        return mapper.map(bid, BidDto.class);
    }
}
