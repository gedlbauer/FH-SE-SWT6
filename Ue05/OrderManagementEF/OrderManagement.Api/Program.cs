using MediatR;
using System.Reflection;
using Microsoft.EntityFrameworkCore;
using OrderManagement.Dal;

var builder = WebApplication.CreateBuilder(args);

ConfigureServices(builder.Services, builder.Configuration, builder.Environment);

WebApplication app = builder.Build();

ConfigureMiddleware(app, app.Environment);
ConfigureEndpoints(app);

app.Run();

//
// Add services to the container.
//
void ConfigureServices(IServiceCollection services, IConfiguration configuration, IHostEnvironment env)
{
    var applicationAssemblies = new[]
    {
    Assembly.GetExecutingAssembly(),                                   // Api
    typeof(OrderManagement.Logic.Mappings.DomainDtoMappings).Assembly, // Logic
    // typeof(OrderManagement.Dal.OrderManagementContext).Assembly,       // Dal
    typeof(OrderManagement.Dtos.CustomerDto).Assembly,                 // Dtos
    typeof(OrderManagement.Domain.Customer).Assembly                   // Domain
  };

    services.AddControllers();
    services.AddEndpointsApiExplorer();

    services.AddRouting(options => options.LowercaseUrls = true);

    services.AddControllers(options => options.ReturnHttpNotAcceptable = true)
            .AddNewtonsoftJson();

    services.AddOpenApiDocument(settings =>
      settings.PostProcess = doc => doc.Info.Title = "Work Hour Logging API");

    services.AddCors(builder =>
      builder.AddDefaultPolicy(policy =>
        policy.AllowAnyOrigin()
              .AllowAnyMethod()
              .AllowAnyHeader()));

    var connectionString = configuration.GetConnectionString("OrderDbConnection");
    services.AddDbContext<OrderManagementContext>(options =>
            options.UseSqlServer(connectionString).EnableSensitiveDataLogging()
    );

    //
    // TODO: Add TransactionCommandBehavior
    //

    services.AddMediatR(applicationAssemblies);

    services.AddAutoMapper(applicationAssemblies);

    if (env.IsDevelopment())
    {
        services.AddHostedService<WorkLog.Api.Util.DbInitializer>();
    }
}

//
// Configure the HTTP request pipeline.
//
void ConfigureMiddleware(IApplicationBuilder app, IHostEnvironment env)
{
    if (env.IsDevelopment())
    {
        app.UseOpenApi();
        app.UseSwaggerUi3();
    }

    app.UseHttpsRedirection();
    app.UseAuthorization();
}

void ConfigureEndpoints(IEndpointRouteBuilder app)
{
    app.MapControllers();
}
