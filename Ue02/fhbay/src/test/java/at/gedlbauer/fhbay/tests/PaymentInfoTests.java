package at.gedlbauer.fhbay.tests;

import at.gedlbauer.fhbay.dal.IPaymentInfoRepository;
import at.gedlbauer.fhbay.dal.PaymentInfoRepository;
import at.gedlbauer.fhbay.domain.*;
import at.gedlbauer.fhbay.util.JpaUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentInfoTests {
    private IPaymentInfoRepository paymentInfos;

    @BeforeEach
    public void initRepo() {
        JpaUtil.setPersistanceUnitName("FHBayTestPU");
        paymentInfos = new PaymentInfoRepository(JpaUtil.getEntityManager());
    }

    @Test
    public void insertPaymentInfo() {
        PaymentInfo toInsert1 = new BankAccount(
                new Customer(
                        "Max",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                "Testbank",
                "0000 0000 0000 0000",
                "AB000YZ"
        );
        PaymentInfo toInsert2 = new CreditCard(
                new Customer(
                        "Max",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                "1234 5678 9012 3456",
                "123",
                YearMonth.of(0, 12),
                "Testbank"
        );
        var tx = JpaUtil.getActiveTransaction();
        paymentInfos.insert(toInsert1);
        paymentInfos.insert(toInsert2);
        assertNotNull(toInsert1.getId());
        assertNotNull(toInsert2.getId());
        assertSame(toInsert1, JpaUtil.getEntityManager().find(PaymentInfo.class, toInsert1.getId()));
        assertSame(toInsert2, JpaUtil.getEntityManager().find(PaymentInfo.class, toInsert2.getId()));
        tx.rollback();
    }

    @Test
    public void removePaymentInfo() {
        PaymentInfo subject = new BankAccount(
                new Customer(
                        "Max",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                "Testbank",
                "0000 0000 0000 0000",
                "AB000YZ"
        );
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(PaymentInfo.class, subject.getId()));
        paymentInfos.delete(subject);
        assertNull(JpaUtil.getEntityManager().find(PaymentInfo.class, subject.getId()));
        tx.rollback();
    }

    @Test
    public void updatePaymentInfo() {
        PaymentInfo subject = new BankAccount(
                new Customer(
                        "Max",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                "Testbank",
                "0000 0000 0000 0000",
                "AB000YZ"
        );
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertNotNull(subject.getId());
        assertSame(subject, JpaUtil.getEntityManager().find(PaymentInfo.class, subject.getId()));
        ((BankAccount) subject).setBankName("updated");
        var idBeforeUpdate = subject.getId();
        paymentInfos.updateOrInsert(subject);
        assertSame(subject, JpaUtil.getEntityManager().find(PaymentInfo.class, subject.getId()));
        assertSame(idBeforeUpdate, subject.getId());
        tx.rollback();
    }

    @Test
    public void getPaymentInfoById() {
        PaymentInfo subject = new BankAccount(
                new Customer(
                        "Max",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                "Testbank",
                "0000 0000 0000 0000",
                "AB000YZ"
        );
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject);
        assertSame(subject, paymentInfos.findById(subject.getId()));
        tx.rollback();
    }

    @Test
    public void getAllPaymentInfos() {
        PaymentInfo subject1 = new BankAccount(
                new Customer(
                        "Franz",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                "Testbank",
                "0000 0000 0000 0000",
                "AB000YZ"
        );
        PaymentInfo subject2 = new BankAccount(
                new Customer(
                        "Max",
                        "Mustermann",
                        "max@mustermann.at",
                        new Address("Musterstrasse 1", "1111", "Musterstadt")),
                "Maxbank",
                "1111 1111 1111 1111",
                "CD11111WX"
        );
        var expected = new ArrayList<PaymentInfo>();
        expected.add(subject1);
        expected.add(subject2);
        var tx = JpaUtil.getActiveTransaction();
        JpaUtil.getEntityManager().persist(subject1);
        JpaUtil.getEntityManager().persist(subject2);
        assertArrayEquals(expected.toArray(), paymentInfos.getAll().toArray());
        tx.rollback();
    }
}
