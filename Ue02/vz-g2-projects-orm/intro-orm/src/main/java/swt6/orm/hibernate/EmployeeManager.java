package swt6.orm.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import swt6.orm.domain.Employee;
import swt6.util.HibernateUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeManager {

  static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
    System.out.flush();
    try {
      return in.readLine();
    }
    catch (Exception e) {
      return promptFor(in, p);
    }
  }

  private static void saveEmployee1(Employee employee) {
    SessionFactory sessionFactory =
     new Configuration()
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

    tx.commit(); // --> session.close()
  }
  public static void main(String[] args) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String availCmds = "commands: quit, insert";

    HibernateUtil.getCurrentSession();

    System.out.println("Hibernate Employee Admin");
    System.out.println(availCmds);
    String userCmd = promptFor(in, "");

    try {

      while (!userCmd.equals("quit")) {

        switch (userCmd) {
          
        case "insert":
          saveEmployee(new Employee(
              promptFor(in, "first name"),
              promptFor(in, "last name" ),
              LocalDate.parse(promptFor(in, "date of birth"), formatter)
          ));
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
    }
  }
}
