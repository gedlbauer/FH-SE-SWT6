package at.gedlbauer.fhbay.api;

import at.gedlbauer.fhbay.domain.Article;
import at.gedlbauer.fhbay.dto.ArticleDto;
import at.gedlbauer.fhbay.dto.CustomerDto;
import at.gedlbauer.fhbay.logic.ArticleService;
import at.gedlbauer.fhbay.logic.CustomerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping(value = "")
    public ArticleDto insertArticle(@RequestBody ArticleDto articleDto) {
        var article = mapper.map(articleDto, Article.class);
        article = articleService.save(article);
        return mapper.map(article, ArticleDto.class);
    }

    @GetMapping(value = "all")
    public List<ArticleDto> getArticles() {
        var articles = articleService.findAll();
        Type articleListType = (new TypeToken<List<ArticleDto>>() {
        }).getType();
        return mapper.map(articles, articleListType);
    }

    @GetMapping(value = "{id}")
    public ArticleDto articleById(@PathVariable("id") Long id) {
        var article = articleService.findById(id);
        if (article.isPresent()) return mapper.map(article.get(), ArticleDto.class);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
    }

    @GetMapping(value = "nameContaining/{name}")
    public List<ArticleDto> getArticlesWhereNameContains(@PathVariable String name){
        var articles = articleService.findByName(name);
        Type articleListType = (new TypeToken<List<ArticleDto>>() {
        }).getType();
        return mapper.map(articles, articleListType);
    }

    @GetMapping(value = "descriptionContaining/{description}")
    public List<ArticleDto> getArticlesWhereDescriptionContains(@PathVariable String description){
        var articles = articleService.findByDescription(description);
        Type articleListType = (new TypeToken<List<ArticleDto>>() {
        }).getType();
        return mapper.map(articles, articleListType);
    }

    @PostMapping(value = "updateSeller")
    public ArticleDto updateSeller(@RequestBody ArticleDto articleDto) {
        var article = articleService.findById(articleDto.getId()).orElseThrow();
        var newSeller = customerService.findById(articleDto.getSeller().getId()).orElseThrow();
        article = articleService.updateSeller(article, newSeller);
        return mapper.map(article, ArticleDto.class);
    }

    @PostMapping(value = "updateBuyer")
    public ArticleDto updateBuyer(@RequestBody ArticleDto articleDto) {
        var article = articleService.findById(articleDto.getId()).orElseThrow();
        var newBuyer = customerService.findById(articleDto.getBuyer().getId()).orElseThrow();
        article = articleService.updateBuyer(article, newBuyer);
        return mapper.map(article, ArticleDto.class);
    }
}
