package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findById(Long id);

    List<Article> findByNameContaining(String name);

    List<Article> findByDescriptionContaining(String description);
}
