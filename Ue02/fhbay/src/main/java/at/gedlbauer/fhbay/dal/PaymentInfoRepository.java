package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.PaymentInfo;

import javax.persistence.EntityManager;
import java.util.List;

public class PaymentInfoRepository extends FHBayRepository<PaymentInfo> implements IPaymentInfoRepository {
    public PaymentInfoRepository(EntityManager em) {
        super(PaymentInfo.class, em);
    }

    @Override
    public List<PaymentInfo> getAll() {
        return em.createQuery("select p from PaymentInfo p").getResultList();
    }
}
