package swt6.spring.worklog.test;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.logic.WorkLogService;
import swt6.util.JpaUtil;

import javax.persistence.EntityManagerFactory;

@SuppressWarnings("Duplicates")
public class LogicTest {

    private static Employee empl1;
    private static Employee empl2;
    private static Employee empl3;

    public static void testSaveEmployee(WorkLogService workLog) {
        empl1 = new Employee("Sepp", "Forcher", LocalDate.of(1935, 12, 12));
        empl2 = new Employee("Alfred", "Kunz", LocalDate.of(1944, 8, 10));
        empl3 = new Employee("Sigfried", "Hinz", LocalDate.of(1954, 5, 3));

        empl1 = workLog.syncEmployee(empl1);
        empl2 = workLog.syncEmployee(empl2);
        empl3 = workLog.syncEmployee(empl3);
    }

    public static void testAddLogbookEntry(WorkLogService workLog) {
        LogbookEntry entry1 = new LogbookEntry("Analyse",
                LocalDateTime.of(2018, 3, 1, 10, 0), LocalDateTime.of(2018, 3, 1, 11, 30));
        LogbookEntry entry2 = new LogbookEntry("Implementierung",
                LocalDateTime.of(2018, 3, 1, 11, 30), LocalDateTime.of(2018, 3, 1, 16, 30));
        LogbookEntry entry3 = new LogbookEntry("Testen",
                LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30));

        empl1.addLogbookEntry(entry1);
        empl1.addLogbookEntry(entry2);
        empl2.addLogbookEntry(entry3);

        empl1 = workLog.syncEmployee(empl1);
        empl2 = workLog.syncEmployee(empl2);
    }

    public static void testFindAll(WorkLogService workLog) {
        workLog.findAllEmployees().forEach(x -> x.getLogbookEntries().forEach(System.out::println));
    }

    private static void testBusinessLogicWithJpaDaos() {
        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext(
                             "swt6/spring/worklog/test/applicationContext-jpa2.xml")) {
            EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);
            WorkLogService workLog = factory.getBean("workLog", WorkLogService.class);

            printTitle("testSaveEmployee", 60, '-');
            testSaveEmployee(workLog);
            printTitle("testAddLogbookEntry", 60, '-');
            testAddLogbookEntry(workLog);
            printTitle("testFindAll", 60, '-');
            JpaUtil.executeInOpenEntityManager(emFactory, () -> {
                testFindAll(workLog);
            });
        }
    }

    public static void main(String[] args) {

        printSeparator(60);
        printTitle("testBusinessLogicWithJpaDaos", 60);
        printSeparator(60);
        testBusinessLogicWithJpaDaos();

//  	printSeparator(60); 
//  	printTitle("testBusinessLogicWithSpringDataRepositories", 60); 
//  	printSeparator(60);
//    testBusinessLogicWithSpringDataRepositories();
    }
}
