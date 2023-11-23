package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TraceAdvice {

    @Before("execution(public * swt6.spring.basics.aop..*.*(..)))")
    public void traceBefore(JoinPoint jp) {
        String methodName = jp.getTarget().getClass().getName() + jp.getSignature().getName();
        System.out.printf("---> %s\n", jp.getSignature().getName());
    }

    @After("execution(public * swt6.spring.basics.aop..*.*(..)))")
    public void traceAfter(JoinPoint jp) {
        String methodName = jp.getTarget().getClass().getName() + jp.getSignature().getName();
        System.out.printf("<--- %s\n", jp.getSignature().getName());
    }

    @Around("execution(public * swt6.spring.basics.aop..*.*find*ById*(..))")
    public Object traceAround(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getTarget().getClass().getName() + pjp.getSignature().getName();
        System.out.printf("around ===> %s\n", pjp.getSignature().getName());
        Object result = pjp.proceed();
        System.out.printf("<=== around %s\n", pjp.getSignature().getName());
        return result;
    }

    @AfterThrowing(pointcut = "execution(public * swt6.spring.basics.aop..*.*(..)))", throwing = "exception")
    public void traceException(JoinPoint jp, Throwable exception) {
        String methodName = jp.getTarget().getClass().getName() + jp.getSignature().getName();
        System.out.printf("    %s in %s\n", exception.getClass().getName(), jp.getSignature().getName());
    }
}
