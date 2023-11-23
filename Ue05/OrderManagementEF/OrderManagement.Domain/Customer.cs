namespace OrderManagement.Domain;

using System;
using System.Collections.Generic;

public class Customer
{
  public Customer(string name, Rating rating) : this(Guid.NewGuid(), name, rating)
  {
  }

  public Customer(Guid id, string name, Rating rating)
  {
    this.Id = id;
    this.Name = name;
    this.Rating = rating;
  }

  public Guid Id { get; set; }

  public string? Name { get; set; }

  public Address? Address { get; set; }

  public Rating Rating { get; set; }

  public decimal? TotalRevenue { get; set; }

  public IList<Order> Orders { get; set; } = new List<Order>();

  public override string ToString() => $"Customer {{ Id: {Id}, Name: {Name}, TotalRevenue: {TotalRevenue} }}";
}

