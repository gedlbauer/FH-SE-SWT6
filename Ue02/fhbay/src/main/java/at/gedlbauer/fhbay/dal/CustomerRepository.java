package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Customer;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomerRepository extends FHBayRepository<Customer> implements ICustomerRepository {
    public CustomerRepository(EntityManager em) {
        super(Customer.class, em);
    }

    @Override
    public List<Customer> getAll() {
        return em.createQuery("select e from Customer e").getResultList();
    }
}
