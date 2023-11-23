package at.gedlbauer.fhbay.dal;

import at.gedlbauer.fhbay.domain.PaymentInfo;

import java.util.List;

public interface IPaymentInfoRepository extends IFHBayRepository<PaymentInfo> {
    @Override
    List<PaymentInfo> getAll();
}
