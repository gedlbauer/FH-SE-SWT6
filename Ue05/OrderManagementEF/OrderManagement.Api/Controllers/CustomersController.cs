namespace OrderManagement.Api.Controllers;

using Microsoft.AspNetCore.Mvc;
using OrderManagement.Dtos;
using MediatR;
using OrderManagement.Logic.Contracts.Queries;

[Route("api/[controller]")]
[ApiController]
[ApiConventionType(typeof(WebApiConventions))]
public class CustomersController : ControllerBase
{
    private readonly ILogger<CustomersController> logger;
    private readonly IMediator mediator;

    public CustomersController(ILogger<CustomersController> logger, IMediator mediator)
    {
        this.logger = logger ?? throw new ArgumentNullException(nameof(logger));
        this.mediator = mediator ?? throw new ArgumentNullException(nameof(mediator));
    }

    [HttpGet]
    public async Task<IEnumerable<CustomerDto>> GetCustomers(Rating? rating)
    {
        return await mediator.Send(new FindCustomersQuery(rating));
    }

    /// <summary>
    /// Provides detailed data for a customer with the specified ID.
    /// </summary>
    /// <param name="customerId">Unique ID of customer</param>
    [HttpGet("{customerId}")]
    public Task<ActionResult<CustomerDto>> GetCustomerById([FromRoute] Guid customerId)
    {
        return Task.FromResult<ActionResult<CustomerDto>>(NotFound());
    }

    [HttpPost]
    public Task<ActionResult<CustomerDto>> CreateCustomer(CustomerForCreationDto customer)
    {
        return Task.FromResult<ActionResult<CustomerDto>>(new StatusCodeResult(StatusCodes.Status501NotImplemented));
    }

    [HttpPut("{customerId}")]
    public Task<ActionResult> UpdateCustomer(Guid customerId, CustomerForUpdateDto customer)
    {
        return Task.FromResult<ActionResult>(new StatusCodeResult(StatusCodes.Status501NotImplemented));
    }

    [HttpDelete("{customerId}")]
    public Task<ActionResult> DeleteCustomer(Guid customerId)
    {
        return Task.FromResult<ActionResult>(new StatusCodeResult(StatusCodes.Status501NotImplemented));
    }
}
