using IntroEF.Dal;
using IntroEF.Domain;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IntroEF.Logic;

public class Commands
{
    public static async Task AddCustomersAsync()
    {
        using var db = new OrderManagementContext();
        var customer1 = new Customer("Blue Cars", Rating.A)
        {
            Address = new(4232, "Hagenberg")
        };
        var customer2 = new Customer("Orange Motorbikes", Rating.B)
        {
            Address = new(4230, "Pregarten")
        };
        db.Customers.AddRange(customer1, customer2);
        await db.SaveChangesAsync();
    }

    public static async Task ListCustomersAsync()
    {
        using var db = new OrderManagementContext();
        var customers = await db.Customers
            .AsNoTracking()
            .Include(x => x.Address)
            .Include(x => x.Orders)
            .ToListAsync();
        foreach (var customer in customers)
        {
            Console.WriteLine(customer);
            if (customer.Address is not null)
            {
                Console.WriteLine($"    {customer.Address}");
            }
            if (customer.Orders.Count > 0)
            {
                Console.WriteLine("  Orders:");
                foreach (var order in customer.Orders)
                {
                    Console.WriteLine($"    {order}");
                }
            }
        }
    }

    public static async Task AddOrdersToCustomerAsync()
    {
        using var db = new OrderManagementContext();
        var customer = await db.Customers.FirstOrDefaultAsync();

        if (customer is null)
        {
            return;
        }
        var order1 = new Order("Computer", DateTime.Now, 2000m)
        {
            Customer = customer
        };
        var order2 = new Order("Rechner", DateTime.Now, 1000m)
        {
            Customer = customer
        };
        await db.Orders.AddRangeAsync(order1, order2);
        await db.SaveChangesAsync();
    }
}
