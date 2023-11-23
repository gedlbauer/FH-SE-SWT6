package at.gedlbauer.fhbay.domain;

import javax.persistence.Entity;
import java.time.YearMonth;

@Entity
public class CreditCard extends PaymentInfo {
    private String cardNumber;
    private String cvv;
    private YearMonth expirationDate;
    private String institute;

    public CreditCard(Customer customer, String cardNumber, String cvv, YearMonth expirationDate, String institute) {
        super(customer);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.institute = institute;
    }

    public CreditCard() {
    }

    // region getters/setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }
    //endregion
}
