package swt6.orm.domain;

import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@Entity
//@Table(name = "TBL_EMPLOYEE")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Variante 1
//@Inheritance(strategy = InheritanceType.JOINED) // Variante 2
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Variante 3
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING) // Variante 3
@DiscriminatorValue("E") // Variante 3
public abstract class Employee implements Serializable {
    @Id
    @GeneratedValue//(strategy = GenerationType.TABLE)
    private Long id;
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private LocalDate dateOfBirth;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY)
//    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    private Set<LogBookEntry> logBookEntries = new HashSet<>();
    //    @OneToOne(cascade = CascadeType.ALL)
    @Embedded
    @AttributeOverride(name = "zipCode", column = @Column(name = "ADDR_ZIPCODE"))
    @AttributeOverride(name = "city", column = @Column(name = "ADDR_CITY"))
    @AttributeOverride(name = "street", column = @Column(name = "ADDR_STREET"))
    private Address address;

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<LogBookEntry> getLogBookEntries() {
        return logBookEntries;
    }

    public void setLogBookEntries(Set<LogBookEntry> logBookEntries) {
        this.logBookEntries = logBookEntries;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Employee.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("dateOfBirth=" + dateOfBirth)
                .add("address=" + address)
                .toString();
    }

    public void addLogBookEntry(LogBookEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("entry must not be null");
        }
        if (entry.getEmployee() != null) {
            entry.getEmployee().logBookEntries.remove(entry);
        }
        logBookEntries.add(entry);
        entry.setEmployee(this);
    }
}
