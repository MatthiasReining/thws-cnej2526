package de.thws.cdi;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@THWSUpperCase
@Interceptor
public class THWSUpperCaseInterceptor {

    @AroundInvoke
    public Object toUpperCase(InvocationContext context) throws Exception {

        Object ret = context.proceed();

        System.out.println("ret: " + ret);

        String upperCaseResult = ((String) ret).toUpperCase();
        return upperCaseResult;
    }

}
