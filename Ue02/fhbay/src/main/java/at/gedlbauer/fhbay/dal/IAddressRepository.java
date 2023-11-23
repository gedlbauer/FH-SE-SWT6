package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.Address;

import java.util.List;

public interface IAddressRepository extends IFHBayRepository<Address> {
    @Override
    List<Address> getAll();
}
