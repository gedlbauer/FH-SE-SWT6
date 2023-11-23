namespace OrderManagement.Dtos;

using Newtonsoft.Json;

public record OrderDto
{
  [JsonProperty(Required = Required.Always)]
  public Guid Id { get; set; }

  [JsonProperty(Required = Required.Always)]
  public DateTimeOffset OrderDate { get; set; }

  [JsonProperty(Required = Required.Always)]
  public string Article { get; set; } = string.Empty;

  [JsonProperty(Required = Required.Always)]
  public decimal TotalPrice { get; set; }

  [JsonProperty(Required = Required.Always)]
  public Guid CustomerId { get; set; }
}
