package at.gedlbauer.fhbay.domain;

import javax.persistence.Entity;

@Entity
public class BankAccount extends PaymentInfo {
    private String bankName;
    private String iban;
    private String bic;

    public BankAccount(Customer customer, String bankName, String iban, String bic) {
        super(customer);
        this.bankName = bankName;
        this.iban = iban;
        this.bic = bic;
    }

    public BankAccount() {
    }
    // region getters/setters
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
    //endregion
}
