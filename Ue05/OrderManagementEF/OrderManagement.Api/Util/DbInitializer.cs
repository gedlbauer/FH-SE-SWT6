using OrderManagement.Dal;
using OrderManagement.Domain;

namespace WorkLog.Api.Util;


public class DbInitializer : BackgroundService
{
    private readonly OrderManagementContext db;
    private readonly IConfiguration configuration;

    public DbInitializer(IServiceScopeFactory scopeFactory, IConfiguration configuration)
    {
        db = scopeFactory.CreateScope()
                         .ServiceProvider
                         .GetRequiredService<OrderManagementContext>();
        this.configuration = configuration;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken = default)
    {
        bool recreateDatabase = configuration.GetValue<bool>("RecreateDatabase", true);
        if (recreateDatabase)
        {
            await db.Database.EnsureDeletedAsync(stoppingToken);
            await db.Database.EnsureCreatedAsync(stoppingToken);
            await InitDatabase(db, stoppingToken);
        }

        await Task.CompletedTask;
    }

    private async Task InitDatabase(OrderManagementContext db, CancellationToken stoppingToken)
    {
        var customer1 = new Customer(new Guid("cccccccc-0000-0000-0000-111111111111"),
                                     "Mayr Immobilien", Rating.B)
        {
            Address = new(4020, "Linz", "Wiener Straße 9")
        };

        db.Orders.AddRange(
          new Order(new Guid("aaaaaaaa-0000-0000-0000-111111111111"),
                    "Surface Book 3", new DateTime(2022, 1, 1), 2850m)
          {
              Customer = customer1
          },
          new Order(new Guid("aaaaaaaa-0000-0000-0000-222222222222"),
                    "Dell Monitor", new DateTime(2022, 2, 2), 250m)
          {
              Customer = customer1
          });

        var customer2 = new Customer(new Guid("cccccccc-0000-0000-0000-222222222222"),
                                     "Software Solutions", Rating.A)
        {
            Address = new(4232, "Habenberg", "Softwarepark 10")
        };

        db.Orders.AddRange(
          new Order(new Guid("aaaaaaaa-0000-0000-0000-333333333333"),
                    "Logitech Maus", new DateTime(2022, 3, 4), 70m)
          {
              Customer = customer2
          },
          new Order(new Guid("aaaaaaaa-0000-0000-0000-444444444444"),
                    "Bose Kopfhörer", new DateTime(2022, 2, 28), 70m)
          {
              Customer = customer2
          });

        await db.Customers.AddRangeAsync(customer1, customer2);
        await db.SaveChangesAsync(stoppingToken);
    }
}

