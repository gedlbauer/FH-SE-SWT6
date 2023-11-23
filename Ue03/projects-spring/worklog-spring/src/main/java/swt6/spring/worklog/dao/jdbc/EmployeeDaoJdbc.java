package swt6.spring.worklog.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoJdbc implements EmployeeDao {

    private static class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            var employee = new Employee();
            employee.setId(rs.getLong("ID"));
            employee.setFirstName(rs.getString("FIRSTNAME"));
            employee.setLastName(rs.getString("LASTNAME"));
            employee.setDateOfBirth(rs.getDate("DATEOFBIRTH").toLocalDate());
            return employee;
        }
    }

    private JdbcTemplate template;

    public EmployeeDaoJdbc(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        final String sql = "select ID, FIRSTNAME, LASTNAME, DATEOFBIRTH from EMPLOYEE where ID=?";

        List<Employee> employees = template.query(sql, new EmployeeMapper(), id);
        if(employees.size() > 1) {
                throw new IncorrectResultSizeDataAccessException(1, employees.size());
        }
        return employees.stream().findFirst();
    }

    @Override
    public List<Employee> findAll() {
        final String sql = "select ID, FIRSTNAME, LASTNAME, DATEOFBIRTH from EMPLOYEE";
        return template.query(sql, new EmployeeMapper());
    }

    // Version 1: Data access code without Spring
//    public void insert(final Employee e) throws DataAccessException {
//        final String sql =
//                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
//                        + "values (?, ?, ?)";
//        try (Connection conn = template.getDataSource().getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, e.getFirstName());
//            stmt.setString(2, e.getLastName());
//            stmt.setDate(3, Date.valueOf(e.getDateOfBirth()));
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.err.println(ex);
//        }
//    }

    // Version 2: Data access code with Spring
//    public void insert(final Employee employee) throws DataAccessException {
//        final String sql =
//                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
//                        + "values (?, ?, ?)";
//        template.update(sql, ps -> {
//            ps.setString(1, employee.getFirstName());
//            ps.setString(2, employee.getLastName());
//            ps.setDate(3, Date.valueOf(employee.getDateOfBirth()));
//        });
//    }

    // Version 3: Data access code with Spring
    public void insert(final Employee employee) throws DataAccessException {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setDate(3, Date.valueOf(employee.getDateOfBirth()));
            return ps;
        }, keyHolder);
        employee.setId(keyHolder.getKey().longValue());
    }

    @Override
    public Employee merge(Employee entity) {
        return null;
    }
}
