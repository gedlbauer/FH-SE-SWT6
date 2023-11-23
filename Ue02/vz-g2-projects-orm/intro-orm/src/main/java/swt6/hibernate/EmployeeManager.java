package swt6.hibernate;

import net.bytebuddy.asm.Advice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import swt6.orm.domain.Employee;
import swt6.util.HibernateUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmployeeManager {

    static String promptFor(BufferedReader in, String p) {
        System.out.print(p + "> ");
        System.out.flush();
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, p);
        }
    }

    // region Save
    private static void saveEmployeeOld(Employee employee) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(employee);
        tx.commit();
        session.close();
        sessionFactory.close();
    }

    private static void saveEmployee(Employee employee) {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.save(employee);
        tx.commit(); // --> implicit session.close();
    }
    // endregion

    private static List<Employee> listEmployees() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query<Employee> query = session.createQuery(
                "SELECT e FROM Employee e",
                Employee.class
        );
        var employees = query.getResultList();
        tx.commit();
        return employees;
    }

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String availCmds = "commands: quit, insert, list";

        HibernateUtil.getCurrentSession();

        System.out.println("Hibernate Employee Admin");
        System.out.println(availCmds);
        String userCmd = promptFor(in, "");

        try {

            while (!userCmd.equals("quit")) {

                switch (userCmd) {

                    case "insert":
                        saveEmployee(new Employee(
                                promptFor(in, "firstName"),
                                promptFor(in, "lastName"),
                                LocalDate.parse(promptFor(in, "dateOfBirth"), formatter)
                        ));
                        break;
                    case "list":
                        for (var employee : listEmployees()) {
                            System.out.println(employee);
                        }
                        break;
                    default:
                        System.out.println("ERROR: invalid command");
                        break;
                }

                System.out.println(availCmds);
                userCmd = promptFor(in, "");
            } // while

        } // try
        catch (Exception ex) {
            ex.printStackTrace();
        } // catch
        finally {
            HibernateUtil.closeSessionFactory();
        } // finally
    }
}
