package com.hunk.learn.guice.app;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.List;

/**
 * Created by hunk on 2015/8/19.
 */
public class ExceptionMethodInterceptor implements MethodInterceptor{
    private ExceptionMethodInterceptor() {
    }

    public static ExceptionMethodInterceptor exception() {
        return new ExceptionMethodInterceptor();
    }


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            final  String methodName = getMethodName(methodInvocation);
        try {
            System.out.println(String.format("method(%s) call with: %s.", methodName,getArgs(methodInvocation)));
            final Object result = methodInvocation.proceed();
            System.out.println(String.format("method(%s) return with %d.", methodName ,result.hashCode()));
            return result;
        } catch (Exception e) {
            System.err.println(String.format("method(%s) error wiht %s.",methodName,getArgs(methodInvocation))+e.getMessage());
            e.printStackTrace();
            throw  e;
        }
    }

    private Object getArgs(MethodInvocation methodInvocation){
        final List<String> args = Lists.newArrayList();
        if (methodInvocation.getArguments() !=null ){
            for (int i = 0; i < methodInvocation.getArguments().length; i++) {
                final  Object arg = methodInvocation.getArguments()[i];
                args.add(arg==null? "null":arg.toString());
            }
        }
        return Joiner.on(",").join(args);
    }

    private String getMethodName(MethodInvocation methodInvocation){
        return  String.format("%s-(%s)",methodInvocation.getMethod().getDeclaringClass().getName(),methodInvocation.getMethod());
    }
}
