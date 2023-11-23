package swt6.orm.jpa;

import swt6.orm.domain.*;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class WorkLogManager<T> {
    public static void main(String[] args) {
        System.out.println("========== create Schema ==========");
        JpaUtil.getEntityManagerFactory();
        PermanentEmployee pe1 = new PermanentEmployee("Franz", "Mayr", LocalDate.of(2000, 1, 1));
        Employee e1 = pe1;
        e1.setAddress(new Address("4716", "Hofkirchen", "Weng 53"));

        TemporaryEmployee te1 = new TemporaryEmployee("Michael", "Huber", LocalDate.of(2001, 2, 2));
        te1.setRenter("Niemand");
        te1.setStartDate(LocalDate.of(2022, 1, 1));
        te1.setEndDate(LocalDate.of(2022, 12, 31));
        te1.setAddress(new Address("4230", "Pregarten", "Praterstraße"));
        Employee e2 = te1;

        LogBookEntry entry1 = new LogBookEntry("Testen", LocalDateTime.now().minusMinutes(5), LocalDateTime.now());
        LogBookEntry entry2 = new LogBookEntry("Dokumentieren", LocalDateTime.now(), LocalDateTime.now().plusMinutes(10));
        LogBookEntry entry3 = new LogBookEntry("Implementieren", LocalDateTime.now().plusMinutes(10), LocalDateTime.now().plusMinutes(30));

        System.out.println("==========  Schema end   ==========");
        try {
            System.out.println("------- insert Employees --------");
            insertEntity(e1);
            insertEntity(e2);

            System.out.println("------- add Logbook Entries --------");
            addLogBookEntries(e1, entry1, entry2);
            addLogBookEntries(e2, entry3);

            System.out.println("------- list Employees --------");
            listEmployees();

            System.out.println("------- test fetching strategies --------");
            testFetchingStrategies();

            System.out.println("------- list entries of employee --------");
            listEntriesOfEmployee(e1);

            System.out.println("------- load employees with logbook entries (fetch join) --------");
            loadEmployeesWithEntries();

            System.out.println("------- list entries of employee CQ --------");
            listEntriesOfEmployeeCQ(e1);
        } finally {
            JpaUtil.closeEntityManagerFactory();
        }

    }

    private static void listEntriesOfEmployeeCQ(Employee employee){
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            var cb = em.getCriteriaBuilder();
            var entryCQ = cb.createQuery(LogBookEntry.class);

            var entry = entryCQ.from(LogBookEntry.class);
            var emplParam = cb.parameter(Employee.class);
            entryCQ.where(cb.equal(entry.get(LogBookEntry_.employee), emplParam)).select(entry);

            var query = em.createQuery(entryCQ);
            query.setParameter(emplParam, employee);
            query.getResultList().forEach(System.out::println);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    private static void loadEmployeesWithEntries() {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            var query = em.createQuery("select e from Employee e join fetch e.logBookEntries");

            List<Employee> employeeList = query.getResultList();
            System.out.println("----- executed query -----");
            employeeList.forEach(employee -> {
                System.out.println(employee);
                employee.getLogBookEntries().forEach(le -> System.out.println("    " + le));
            });
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    private static void listEntriesOfEmployee(Employee employee) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            var entriesQuery = em.createQuery("select entry from LogBookEntry entry where entry.employee.id = :employeeId", LogBookEntry.class);
            entriesQuery.setParameter("employeeId", employee.getId());
            entriesQuery.getResultList().forEach(System.out::println);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    private static void testFetchingStrategies() {
        Long entryId = null;
        Long employeeId = null;
        // prepare
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            Optional<LogBookEntry> entry = em.createQuery("select le from LogBookEntry le", LogBookEntry.class)
                    .setMaxResults(1)
                    .getResultList()
                    .stream().findAny();
            if (entry.isEmpty()) {
                return;
            }
            entryId = entry.get().getId();


            Optional<Employee> employee = em.createQuery("select e from Employee e", Employee.class)
                    .setMaxResults(1)
                    .getResultList()
                    .stream().findAny();
            if (employee.isEmpty()) {
                return;
            }
            employeeId = employee.get().getId();


            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        // LogBookEntry -> Employee
        System.out.println("################## LogBookEntry -> Employee ###################");
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            System.out.println("###> Fetching LogBookEntry...");
            LogBookEntry entry = em.find(LogBookEntry.class, entryId);
            System.out.println("###> Fetched LogBookEntry...");

            System.out.println("###> Fetching associated Employee...");
            Employee employee1 = entry.getEmployee();
            System.out.println("###> Fetched associated Employee...");

            System.out.println("###> Accessing associated Employee...");
            System.out.println(employee1);
            System.out.println("###> Accessed associated Employee...");


            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }


        // Employee -> LogBookEntry
        System.out.println("################## Employee -> LogBookEntry ###################");
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            System.out.println("###> Fetching Employee...");
            Employee employee = em.find(Employee.class, employeeId);
            System.out.println("###> Fetched Employee...");

            System.out.println("###> Fetching Entries...");
            var entries = employee.getLogBookEntries();
            System.out.println("###> Fetched Entries...");

            System.out.println("###> Accessing Entries...");
            entries.forEach(System.out::println);
            System.out.println("###> Accessed Entries...");

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    private static void insertEmployeeOld(Employee employee) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("WorkLogPU");
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = factory.createEntityManager();
            tx = em.getTransaction();

            tx.begin();
            em.persist(employee);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private static <T> void insertEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            em.persist(entity);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    private static <T> T saveEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            entity = em.merge(entity);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return entity;
    }

    private static Employee addLogBookEntries(Employee employee, LogBookEntry... entries) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            for (var entry : entries) {
                employee.addLogBookEntry(entry);
                employee = em.merge(employee);
            }
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return employee;
    }

    private static void listEmployees() {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            var employeeList = em.createQuery("select e from Employee e", Employee.class).getResultList();
            for (Employee employee : employeeList) {
                System.out.println(employee);
                if (!employee.getLogBookEntries().isEmpty()) {
                    System.out.println("    entries");
                    employee.getLogBookEntries().forEach(entry -> System.out.println("    " + entry));
                }
            }
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }
}
