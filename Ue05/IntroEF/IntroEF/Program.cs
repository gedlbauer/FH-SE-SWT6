using IntroEF.Dal;
using IntroEF.Logic;
using IntroEF.Utils;

Console.WriteLine("IntroEF");

PrintUtil.PrintTitle("Create DB Schema");
using (var db = new OrderManagementContext())
{
    await DatabaseUtil.CreateDatabaseAsync(db, true);
}

PrintUtil.PrintTitle("AddCustomers");
await Commands.AddCustomersAsync();

PrintUtil.PrintTitle("AddOrdersToCustomerAsync");
await Commands.AddOrdersToCustomerAsync();

PrintUtil.PrintTitle("ListCustomers");
await Commands.ListCustomersAsync();
