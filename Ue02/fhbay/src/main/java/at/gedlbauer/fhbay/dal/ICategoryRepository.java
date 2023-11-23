package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Address;
import at.gedlbauer.fhbay.domain.Category;

import java.util.List;

public interface ICategoryRepository extends IFHBayRepository<Category> {

    @Override
    List<Category> getAll();
}
