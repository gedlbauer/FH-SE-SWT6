namespace OrderManagement.Api.Controllers;

using System;
using Microsoft.AspNetCore.Mvc;

public static class StatusInfo
{
  public static ProblemDetails CustomerAlreadyExists(Guid customerId) => new ProblemDetails
  {
    Title = "Conflicting customer IDs",
    Detail = $"Customer wid ID '{customerId}' already exists"
  };

  public static ProblemDetails InvalidCustomerId(Guid customerId) => new ProblemDetails
  {
    Title = "Invalid customer ID",
    Detail = $"Customer with ID '{customerId}' does not exist"
  };

  public static ProblemDetails OrderAlreadyExists(Guid orderId) => new ProblemDetails
  {
    Title = "Conflicting order IDs",
    Detail = $"Order wid ID '{orderId}' already exists"
  };


  public static ProblemDetails InvalidOrderId(Guid orderId) => new ProblemDetails
  {
    Title = "Invalid order ID",
    Detail = $"Order with ID '{orderId}' does not exist"
  };
}
