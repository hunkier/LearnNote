package com.hunk.java8;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TestAnnotation {

    @Test
    public void test1()throws Exception{
        Class<TestAnnotation> clazz = TestAnnotation.class;
        Method method = clazz.getMethod("show",String.class);

        MyAnnotation[] annotations = method.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation annotation : annotations) {
            System.out.println(annotation.value());
        }

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                Class<? extends Annotation> clases = annotation.annotationType();
                if (clases.equals(MyAnnotation.class)){
                    MyAnnotation myAnnotation = (MyAnnotation) annotation;
                    System.out.println(myAnnotation.value());
                }
            }
        }

    }

    @MyAnnotation("Hello")
    @MyAnnotation("Lambda")
    public void show(@MyAnnotation("test" )String str){

    }
}
