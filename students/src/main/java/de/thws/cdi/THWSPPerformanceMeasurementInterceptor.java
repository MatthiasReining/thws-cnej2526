package de.thws.cdi;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@THWSPerformanceMeasurement
@Interceptor
public class THWSPPerformanceMeasurementInterceptor {

    @AroundInvoke
    public Object performanceMeasurement(InvocationContext context) throws Exception {

        long startTime = System.nanoTime();
        Object ret = context.proceed();

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Method " + context.getMethod().getName() + " executed in " + duration + " ms");

        return ret;
    }

}
