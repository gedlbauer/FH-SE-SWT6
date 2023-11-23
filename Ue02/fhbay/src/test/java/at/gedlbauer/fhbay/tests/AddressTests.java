package at.gedlbauer.fhbay.tests;

import at.gedlbauer.fhbay.dal.AddressRepository;
import at.gedlbauer.fhbay.dal.IAddressRepository;
import at.gedlbauer.fhbay.domain.Address;
import at.gedlbauer.fhbay.util.JpaUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTests {

    private IAddressRepository addresses;

    @BeforeEach
    public void initRepo() {
        JpaUtil.setPersistanceUnitName("FHBayTestPU");
        addresses = new AddressRepository(JpaUtil.getEntityManager());
    }

    @Test
    public void insertAddress() {
        Address toInsert = new Address("Musterstrasse 1", "1111", "Musterstadt");
        var tx = JpaUtil.getActiveTransaction();
        addresses.insert(toInsert);
        assertNotNull(toInsert.getId());
        assertSame(toInsert, JpaUtil.getEntityManager().find(Address.class, toInsert.getId()));
        tx.rollback();
    }

    @Test
    public void removeAddress() {
        Address subject = new Address("Musterstrasse 1", "1111", "Musterstadt");
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(Address.class, subject.getId()));
        addresses.delete(subject);
        assertNull(JpaUtil.getEntityManager().find(Address.class, subject.getId()));
        tx.rollback();
    }

    @Test
    public void updateAddress() {
        Address subject = new Address("Musterstrasse 1", "1111", "Musterstadt");
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(Address.class, subject.getId()));
        subject.setCity("Musterdorf");
        var idBeforeUpdate = subject.getId();
        addresses.updateOrInsert(subject);
        assertSame(subject, JpaUtil.getEntityManager().find(Address.class, subject.getId()));
        assertSame(idBeforeUpdate, subject.getId());
        tx.rollback();
    }

    @Test
    public void getAddressById() {
        Address subject = new Address("Musterstrasse 1", "1111", "Musterstadt");
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertSame(subject, addresses.findById(subject.getId()));
        tx.rollback();
    }

    @Test
    public void getAllAddresss() {
        Address subject1 = new Address("Musterstrasse 1", "1111", "Musterstadt");
        Address subject2 = new Address("Musterstrasse 2", "2222", "Musterdorf");
        var expected = new ArrayList<Address>();
        expected.add(subject1);
        expected.add(subject2);
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject1);
        JpaUtil.getEntityManager().persist(subject2);
        assertArrayEquals(expected.toArray(), addresses.getAll().toArray());
        tx.rollback();
    }
}
