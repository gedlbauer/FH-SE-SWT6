package swt6.orm.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

public class Employee implements Serializable {
  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;

  public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
    this.firstName   = firstName;
    this.lastName    = lastName;
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

  @Override
  public String toString() {
    return new StringJoiner(", ", Employee.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .add("dateOfBirth=" + dateOfBirth)
        .toString();
  }
}
