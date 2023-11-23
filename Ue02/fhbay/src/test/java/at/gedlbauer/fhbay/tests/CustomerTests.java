package at.gedlbauer.fhbay.tests;

import at.gedlbauer.fhbay.dal.CustomerRepository;
import at.gedlbauer.fhbay.dal.ICustomerRepository;
import at.gedlbauer.fhbay.domain.Address;
import at.gedlbauer.fhbay.domain.Customer;
import at.gedlbauer.fhbay.util.JpaUtil;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTests {
    private ICustomerRepository customers;
    @BeforeEach
    public void initRepo() {
        JpaUtil.setPersistanceUnitName("FHBayTestPU");
        customers = new CustomerRepository(JpaUtil.getEntityManager());
    }

    @Test
    public void insertCustomer() {
        Customer toInsert = new Customer(
                "Max",
                "Mustermann",
                "max@mustermann.at",
                new Address("Musterstrasse 1", "1111", "Musterstadt"));
        var tx = JpaUtil.getActiveTransaction();
        customers.insert(toInsert);
        assertNotNull(toInsert.getId());
        assertSame(toInsert, JpaUtil.getEntityManager().find(Customer.class, toInsert.getId()));
        tx.rollback();
    }

    @Test
    public void removeCustomer(){
        Customer subject = new Customer(
                "Max",
                "Mustermann",
                "max@mustermann.at",
                new Address("Musterstrasse 1", "1111", "Musterstadt"));
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(Customer.class, subject.getId()));
        customers.delete(subject);
        assertNull(JpaUtil.getEntityManager().find(Customer.class, subject.getId()));
        tx.rollback();
    }

    @Test
    public void updateCustomer(){
        Customer subject = new Customer(
                "Max",
                "Mustermann",
                "max@mustermann.at",
                new Address("Musterstrasse 1", "1111", "Musterstadt"));
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(Customer.class, subject.getId()));
        subject.setFirstName("Susi");
        var idBeforeUpdate = subject.getId();
        customers.updateOrInsert(subject);
        assertSame(subject, JpaUtil.getEntityManager().find(Customer.class, subject.getId()));
        assertSame(idBeforeUpdate, subject.getId());
        tx.rollback();
    }

    @Test
    public void getCustomerById(){
        Customer subject = new Customer(
                "Max",
                "Mustermann",
                "max@mustermann.at",
                new Address("Musterstrasse 1", "1111", "Musterstadt"));
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertSame(subject, customers.findById(subject.getId()));
        tx.rollback();
    }

    @Test
    public void getAllCustomers(){
        Customer subject1 = new Customer(
                "Max",
                "Mustermann",
                "max@mustermann.at",
                new Address("Musterstrasse 1", "1111", "Musterstadt"));
        Customer subject2 = new Customer(
                "Susi",
                "Musterfrau",
                "susi@musterfrau.at",
                new Address("Musterstrasse 2", "2222", "Musterdorf"));
        var expected = new ArrayList<Customer>();
        expected.add(subject1);
        expected.add(subject2);
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject1);
        JpaUtil.getEntityManager().persist(subject2);
        assertArrayEquals(expected.toArray(), customers.getAll().toArray());
        tx.rollback();
    }
}
