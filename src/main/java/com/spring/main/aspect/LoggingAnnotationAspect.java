package com.spring.main.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@ConditionalOnProperty(name="annotation.logging.aspect.enable", havingValue = "true", matchIfMissing=false)
public class LoggingAnnotationAspect {

}
