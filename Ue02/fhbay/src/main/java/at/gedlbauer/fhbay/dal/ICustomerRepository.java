package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Customer;

import java.util.List;

public interface ICustomerRepository extends IFHBayRepository<Customer> {
    @Override
    List<Customer> getAll();
}
