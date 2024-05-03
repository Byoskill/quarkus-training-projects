package com.byoskill.api.utils;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.jboss.logging.Logger;

import java.lang.reflect.Method;

@Logged
@Priority(2020)
@Interceptor
public class LoggingInterceptor {
    @Inject
    Logger logger;

    @AroundInvoke
    Object logInvocation(final InvocationContext context) throws Exception {
        final Method method = context.getMethod();
        final var params = method.getParameters();
        logger.info("Calling " + method.getName() + " with " + params.length + " parameters");
        for (int i = 0; i < params.length; i++) {
            logger.info("Parameter " + i + " is " + params[i].getName() + " of type " + params[i].getType() + " and value " + context.getParameters()[i]);
        }
        final Object ret = context.proceed();
        return ret;
    }

}