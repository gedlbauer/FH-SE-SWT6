package swt6.spring.worklog.logic;

import org.springframework.beans.factory.annotation.Autowired;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import java.util.List;
import java.util.Optional;

public class WorkLogServiceImpl1 implements WorkLogService {

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    private EmployeeDao employeeDao;

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeDao.merge(employee);
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeDao.findById(id);
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeDao.findAll();
    }
}
