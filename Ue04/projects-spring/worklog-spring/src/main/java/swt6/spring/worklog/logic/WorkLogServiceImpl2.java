package swt6.spring.worklog.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;


@Service("workLog")
@Transactional
public class WorkLogServiceImpl2 implements WorkLogService {

    @Autowired
    private EmployeeRepository employeeRepo;

    public WorkLogServiceImpl2() {
    }

    public void setEmployeeRepo(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Transactional(readOnly = true)
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee syncEmployee(Employee employee) {
        return employeeRepo.saveAndFlush(employee);
    }

}
