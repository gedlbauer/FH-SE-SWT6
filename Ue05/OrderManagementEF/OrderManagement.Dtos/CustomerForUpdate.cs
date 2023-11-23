namespace OrderManagement.Dtos;

using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

public record CustomerForUpdateDto
{
  [JsonProperty(Required = Required.Always)]
  public string Name { get; set; } = string.Empty;

  [JsonProperty(Required = Required.Always)]
  [JsonConverter(typeof(StringEnumConverter))]
  public Rating Rating { get; set; }
}
