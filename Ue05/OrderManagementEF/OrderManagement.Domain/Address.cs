namespace OrderManagement.Domain;

public class Address
{
  public Address(int zipCode, string city, string? street = null)
  {
    ZipCode = zipCode;
    City = city;
    Street = street;
  }

  public int ZipCode { get; set; }
  public string City { get; set; }
  public string? Street { get; set; }

  public override string ToString() => $"Address {{ ZipCode: {ZipCode}, City: {City}, Street: {Street ?? ""} }}";
}
