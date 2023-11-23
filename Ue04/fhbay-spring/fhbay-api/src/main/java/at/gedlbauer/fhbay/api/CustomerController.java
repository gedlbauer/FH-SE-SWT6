package at.gedlbauer.fhbay.api;

import at.gedlbauer.fhbay.domain.Customer;
import at.gedlbauer.fhbay.dto.CustomerDto;
import at.gedlbauer.fhbay.logic.CustomerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;
import java.util.List;


@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "all")
    public List<CustomerDto> getCustomers() {
        var customers = customerService.findAll();
        Type customerListType = (new TypeToken<List<CustomerDto>>() {
        }).getType();
        return mapper.map(customers, customerListType);
    }

    @GetMapping(value = "{id}")
    public CustomerDto customerById(@PathVariable("id") Long id) {
        var customer = customerService.findById(id);
        if (customer.isPresent()) return mapper.map(customer.get(), CustomerDto.class);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
    }
}
