namespace OrderManagement.Dtos;

using Newtonsoft.Json;

public record AddressDto
{
  [JsonProperty(Required = Required.Always)]
  public int ZipCode { get; set; }

  [JsonProperty(Required = Required.Always)]
  public string City { get; set; } = string.Empty;

  [JsonProperty(Required = Required.Always)]
  public string? Street { get; set; }
}
