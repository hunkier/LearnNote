package cn.hunkier.spring.springbucks.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect  {

//    @Around("execution(* cn.hunkier.spring.springbucks.repository..*(..))")
    @Around("repositoryOps()")
    public Object logPerformance(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String name = "-";
        String result = "Y";
        try {
            name = pjp.getSignature().toLongString();
            return pjp.proceed();
        } catch (Throwable t){
            result = "N";
            throw t;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("{}; {}; {} ms",name,result,endTime-startTime);
        }
    }

    @Pointcut("execution(* cn.hunkier.spring.springbucks.repository..*(..))")
    private void repositoryOps() {}
}
