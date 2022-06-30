package fr.af.userregistration.config;

import java.util.Arrays;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Aspect
@Configuration
public class LoggingAspect {

  private static Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
  private static final String separator = "====================";

  @Around("execution(* fr.af.userregistration.service.implementation.*.*(*))")
  public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();

    final StopWatch stopWatch = new StopWatch();

    stopWatch.start();
    Object result = proceedingJoinPoint.proceed();
    stopWatch.stop();

    LOGGER.info("Execution time of " + className + "." + methodName + " :: "
        + stopWatch.getTotalTimeMillis() + " ms");

    LOGGER.info(separator);

    return result;
  }

  @Pointcut("execution(* fr.af.userregistration.service.implementation.*.*(*))")
  private void businessService() {
  }

  @Before("businessService()")
  public void doBeforeTask(JoinPoint joinPoint) {
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info(joinPoint.getSignature().toShortString());
      List<Object> args = Arrays.asList(joinPoint.getArgs());
      if (!args.isEmpty()) {
        args.forEach(
            arg -> LOGGER.info("[IN " + arg.getClass().getSimpleName() + "]: " + arg.toString()));
      }
    }
  }

  @AfterReturning(pointcut = "businessService()", returning = "retVal")
  public void doAfterReturn(Object retVal) {
    if (retVal != null) {
      LOGGER.info("[OUT " + retVal.getClass().getSimpleName() + "]: " + retVal.toString());
    }
    LOGGER.info(separator);
  }

  @AfterThrowing(pointcut = "businessService()", throwing = "error")
  public void doAfterReturn(Throwable error) {
    if (error != null) {
      LOGGER.error(error.getClass().getSimpleName());
    }
    LOGGER.info(separator);
  }
}
