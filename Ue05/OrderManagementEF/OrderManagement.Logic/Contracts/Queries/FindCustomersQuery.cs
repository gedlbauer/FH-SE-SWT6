using OrderManagement.Dtos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderManagement.Logic.Contracts.Queries
{
    public record FindCustomersQuery(Dtos.Rating? Rating = null) : IQuery<IEnumerable<CustomerDto>>;
}
