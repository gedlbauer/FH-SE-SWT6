namespace OrderManagement.Dtos;

using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

public record CustomerForCreationDto
{
  [JsonProperty(Required = Required.Always)]
  public string Name { get; set; } = String.Empty;

  [JsonProperty(Required = Required.Always)]
  [JsonConverter(typeof(StringEnumConverter))]
  public Rating Rating { get; set; }

  public AddressDto? Address { get; set; }
}
