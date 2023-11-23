package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Address;

import javax.persistence.EntityManager;
import java.util.List;

public class AddressRepository extends FHBayRepository<Address> implements IAddressRepository {
    public AddressRepository(EntityManager em) {
        super(Address.class, em);
    }

    @Override
    public List<Address> getAll() {
        return em.createQuery("select a from Address a").getResultList();
    }
}
