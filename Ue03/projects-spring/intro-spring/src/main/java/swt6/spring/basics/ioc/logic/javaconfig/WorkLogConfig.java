package swt6.spring.basics.ioc.logic.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.logic.annotationconfig.WorkLogServiceImpl;
import swt6.spring.basics.ioc.util.Logger;

@Configuration
@ComponentScan(basePackageClasses = Logger.class)
public class WorkLogConfig {

    @Bean("worklog")
    public WorkLogService getWorkLogService() {
        return new WorkLogServiceImpl();
    }
}
