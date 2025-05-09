package jpabook.jpashop.aop;

import jpabook.jpashop.aop.Trace.LogTrace;
import jpabook.jpashop.aop.Trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogTraceAspect {
    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* jpabook.jpashop.controller..*(..)) || execution(* jpabook.jpashop.service..*(..)) || execution(* jpabook.jpashop.repository..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try{
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);
            Object result = joinPoint.proceed();
            logTrace.end(status);
            return result;
        }
        catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
