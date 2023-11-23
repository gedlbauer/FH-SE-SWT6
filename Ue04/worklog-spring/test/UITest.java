package swt6.spring.worklog.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

@SuppressWarnings("Duplicates")
public class UITest {

  private static void uiTest() {

    // create domain objects

    Employee empl1 = new Employee("Sepp", "Forcher", LocalDate.of(1935, 12, 12));
    Employee empl2 = new Employee("Alfred", "Kunz", LocalDate.of(1944, 8, 10));
    Employee empl3 = new Employee("Sigfried", "Hinz", LocalDate.of(1954, 5, 3));

    LogbookEntry entry1 = new LogbookEntry("Analyse", 
        LocalDateTime.of(2018, 3, 1, 10, 0), LocalDateTime.of(2018, 3, 1, 11, 30));
    LogbookEntry entry2 = new LogbookEntry("Implementierung", 
        LocalDateTime.of(2018, 3, 1, 11, 30), LocalDateTime.of(2018, 3, 1, 16, 30));
    LogbookEntry entry3 = new LogbookEntry("Testen", 
        LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30));

    try (AbstractApplicationContext factory =
           new ClassPathXmlApplicationContext("swt6/spring/worklog/test/applicationContext-jpa1.xml")) {
 
			printTitle("saveEmployees", 60, '-');
			//
			// Insert your code here
			//
			
			printTitle("findAll", 60, '-');
			//
			// Insert your code here
			//
    }
  }

  public static void main(String[] args) {
  	printSeparator(60); printTitle("UITest (JPA)", 60); printSeparator(60);
    uiTest();
  }
}
