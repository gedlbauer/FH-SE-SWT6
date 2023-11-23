package swt6.spring.basics.hello;

public class GreetingServiceImpl implements GreetingService, AutoCloseable{
    private String message = "<default message>";
    @Override
    public void sayHello() {
        System.out.println(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void destroy() {
        System.out.println("GreetingServiceImpl.destroy()");
    }

    @Override
    public void close() throws Exception {
        System.out.println("GreetingServiceImpl.close()");
    }
}
