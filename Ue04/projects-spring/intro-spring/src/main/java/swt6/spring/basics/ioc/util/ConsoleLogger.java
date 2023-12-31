package swt6.spring.basics.ioc.util;

import org.springframework.stereotype.Component;

@Log(Log.Type.STANDARD)
@Component
public class ConsoleLogger implements Logger {

  private String prefix = "Log";

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public void log(String msg) {
    System.out.format("%s: %s%n", prefix, msg);
  }
}
