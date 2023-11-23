using AutoMapper;

namespace OrderManagement.Logic.Mappings
{
  public class DomainDtoMappings : Profile
  {
    public DomainDtoMappings()
    {
      CreateMap<Domain.Customer, Dtos.CustomerDto>();
      CreateMap<Dtos.CustomerForCreationDto, Domain.Customer>();
      CreateMap<Dtos.CustomerForUpdateDto, Domain.Customer>().ReverseMap();

      CreateMap<Domain.Address, Dtos.AddressDto>().ReverseMap();

      CreateMap<Domain.Order, Dtos.OrderDto>()
        .ForMember(orderDto => orderDto.CustomerId,
                   c => c.MapFrom(order => order.Customer!.Id));
      CreateMap<Dtos.OrderForCreationDto, Domain.Order>();
      CreateMap<Dtos.OrderForUpdateDto, Domain.Order>();
    }
  }
}
