namespace OrderManagement.Dtos;

using Newtonsoft.Json;

public record OrderForCreationDto
{
  [JsonProperty(Required = Required.Always)]
  public string Article { get; set; } = string.Empty;

  [JsonProperty(Required = Required.Always)]
  public DateTimeOffset OrderDate { get; set; }

  [JsonProperty(Required = Required.Always)]
  public decimal TotalPrice { get; set; }
}
