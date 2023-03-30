package com.a304.wildworker.event.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class EventPublisherAspect implements ApplicationEventPublisherAware {

    private final ThreadLocal<Boolean> appliedLocal = new ThreadLocal<>();
    private ApplicationEventPublisher publisher;

//    @Before("execution(* com.a304.wildworker.*.*(..)) && @annotation(com.a304.wildworker.event.common.EventPublish)")
//    public void beforeMethodCall(JoinPoint joinPoint) {
//        // Check if the calling method has the @EventPublish annotation
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        if (!method.isAnnotationPresent(EventPublish.class)) {
//            throw new RuntimeException("Method " + method.getName() + " can only be called from methods with @EventPublish annotation");
//        }
//    }

    @Around("@annotation(com.a304.wildworker.event.common.EventPublish)")
    public Object handleEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        Boolean appliedValue = appliedLocal.get();
        boolean nested;

        if (appliedValue != null && appliedValue) {
            nested = true;
        } else {
            nested = false;
            appliedLocal.set(Boolean.TRUE);
        }

        if (!nested) {
            Events.setPublisher(publisher);
        }

        try {
            return joinPoint.proceed();
        } finally {
            if (!nested) {
                Events.reset();
                appliedLocal.remove();
            }
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.publisher = eventPublisher;
    }
}
