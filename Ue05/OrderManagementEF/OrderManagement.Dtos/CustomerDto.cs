namespace OrderManagement.Dtos;

using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

public record CustomerDto
{
  [JsonProperty(Required = Required.Always)]
  public Guid Id { get; set; }

  [JsonProperty(Required = Required.Always)]
  public string Name { get; set; } = string.Empty;

  [JsonProperty(Required = Required.Always)]
  [JsonConverter(typeof(StringEnumConverter))]
  public Rating Rating { get; set; }

  public decimal? TotalRevenue { get; set; }

  public AddressDto? Address { get; set; }
}
