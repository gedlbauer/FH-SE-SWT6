using Microsoft.EntityFrameworkCore;
using OrderManagement.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderManagement.Dal;

public class OrderManagementContext : DbContext
{
    public DbSet<Customer> Customers => Set<Customer>();
    public DbSet<Order> Orders => Set<Order>();

    public OrderManagementContext(DbContextOptions options) : base(options)
    {

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
