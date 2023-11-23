using IntroEF.Domain;
using IntroEF.Utils;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IntroEF.Dal;

public class OrderManagementContext : DbContext
{
    public DbSet<Customer> Customers => Set<Customer>();
    public DbSet<Order> Orders => Set<Order>();
    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder
            .UseSqlServer(ConfigurationUtil.GetConnectionString("OrderDbConnection"))
            .EnableSensitiveDataLogging()
            .LogTo(Console.WriteLine, Microsoft.Extensions.Logging.LogLevel.Warning);
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        //modelBuilder.Entity<Customer>()
        //    .ToTable("TBL_CUSTOMER")
        //    .HasKey(c => c.Id);
        modelBuilder.Entity<Customer>()
            .Property(c => c.TotalRevenue)
            .HasPrecision(18, 3);
        modelBuilder.Entity<Customer>()
            .OwnsOne(c => c.Address);
    }
}
