package at.gedlbauer.fhbay.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Category parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Category> subCategories = new HashSet<>();

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public Category() {
    }

    // region getters/setters
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

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }
    // endregion
}
