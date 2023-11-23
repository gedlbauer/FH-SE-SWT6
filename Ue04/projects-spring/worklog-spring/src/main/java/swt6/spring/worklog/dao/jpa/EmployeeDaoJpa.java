package swt6.spring.worklog.dao.jpa;

import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoJpa implements EmployeeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(em.find(Employee.class, id));
    }

    @Override
    public List<Employee> findAll() {
        return em.createQuery("select e from Employee e").getResultList();
    }

    @Override
    public void insert(Employee employee) {
        em.persist(employee);
    }

    @Override
    public Employee merge(Employee employee) {
        return em.merge(employee);
    }
}
