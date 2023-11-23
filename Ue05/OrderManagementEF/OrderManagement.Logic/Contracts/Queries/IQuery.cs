using MediatR;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderManagement.Logic.Contracts.Queries
{
    public interface IQuery<out TResponse> : IRequest<TResponse>
    {
    }
}
