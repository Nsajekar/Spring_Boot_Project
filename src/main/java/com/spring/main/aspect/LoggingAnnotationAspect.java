package com.spring.main.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnProperty(name="annotation.logging.aspect.enable", havingValue = "true", matchIfMissing=false)
public class LoggingAnnotationAspect {

}
