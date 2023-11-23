package at.gedlbauer.fhbay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class AppConfig {
    @Bean
    public TaskScheduler auctionScheduler(){
        return new ThreadPoolTaskScheduler();
    }
}
