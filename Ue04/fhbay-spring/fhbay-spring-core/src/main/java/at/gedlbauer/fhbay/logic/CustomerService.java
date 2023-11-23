package at.gedlbauer.fhbay.logic;

import at.gedlbauer.fhbay.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
}
