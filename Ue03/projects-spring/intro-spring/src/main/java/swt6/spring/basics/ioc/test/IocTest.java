package swt6.spring.basics.ioc.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.logic.factorybased.WorkLogServiceImpl;
import swt6.spring.basics.ioc.logic.javaconfig.WorkLogConfig;
import swt6.util.PrintUtil;

public class IocTest {

    private static void testSimple() {
        WorkLogServiceImpl workLogService = new WorkLogServiceImpl();
        workLogService.findEmployeeById(1L);
        workLogService.findEmployeeById(99L);
        workLogService.findAllEmployees();
    }

    private static void testXmlConfig() {
        try (AbstractApplicationContext factory
                     = new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/test/applicationContext-xml-config.xml")) {
            WorkLogService workLogService = factory.getBean("worklog-constructor-injected", WorkLogService.class);
            workLogService.findEmployeeById(1L);
            workLogService.findEmployeeById(99L);
            workLogService.findAllEmployees();
        }
    }

    private static void testAnnotationConfig() {
        try (AbstractApplicationContext factory
                     = new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/test/applicationContext-annotation-config.xml")) {
            WorkLogService workLogService = factory.getBean("worklog", WorkLogService.class);
            workLogService.findEmployeeById(1L);
            workLogService.findEmployeeById(99L);
            workLogService.findAllEmployees();
        }
    }

    private static void testJavaConfig() {
        try (AbstractApplicationContext factory
                     = new AnnotationConfigApplicationContext(WorkLogConfig.class)) {
            WorkLogService workLogService = factory.getBean("worklog", WorkLogService.class);
            workLogService.findEmployeeById(1L);
            workLogService.findEmployeeById(99L);
            workLogService.findAllEmployees();
        }
    }

    public static void main(String[] args) {
//        PrintUtil.printTitle("testSimple", 60);
//        testSimple();
//        PrintUtil.printSeparator(60);

//        PrintUtil.printTitle("testXmlConfig", 60);
//        testXmlConfig();
//        PrintUtil.printSeparator(60);

//        PrintUtil.printTitle("testAnnotationConfig", 60);
//        testAnnotationConfig();
//        PrintUtil.printSeparator(60);

        PrintUtil.printTitle("testJavaConfig", 60);
        testJavaConfig();
        PrintUtil.printSeparator(60);
    }
}
