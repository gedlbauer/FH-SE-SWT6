using AutoMapper;
using AutoMapper.QueryableExtensions;
using MediatR;
using Microsoft.EntityFrameworkCore;
using OrderManagement.Dal;
using OrderManagement.Dtos;
using OrderManagement.Logic.Contracts.Queries;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderManagement.Logic.Handlers.Queries
{
    internal class FindCustomersHandler : IRequestHandler<FindCustomersQuery, IEnumerable<CustomerDto>>
    {
        private readonly OrderManagementContext db;
        private readonly IMapper mapper;

        public FindCustomersHandler(OrderManagementContext db, IMapper mapper)
        {
            this.db = db;
            this.mapper = mapper;
        }

        public async Task<IEnumerable<CustomerDto>> Handle(FindCustomersQuery query, CancellationToken cancellationToken)
        {
            if (query.Rating is null)
            {
                return await db.Customers.AsNoTracking()
                    .Include(x => x.Address)
                    .ProjectTo<CustomerDto>(mapper.ConfigurationProvider)
                    .ToListAsync();
            }
            else
            {
                var domainRating = mapper.Map<Domain.Rating>(query.Rating);
                return await db.Customers.AsNoTracking()
                    .Where(x => x.Rating == domainRating)
                    .Include(x => x.Address)
                    .ProjectTo<CustomerDto>(mapper.ConfigurationProvider)
                    .ToListAsync();
            }
        }
    }
}
