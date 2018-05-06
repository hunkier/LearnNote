package com.hunk.java8;

import org.junit.Test;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Repeatable(MyAnnotations.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, TYPE, FIELD, PARAMETER,CONSTRUCTOR,LOCAL_VARIABLE,ANNOTATION_TYPE,PACKAGE,TYPE_PARAMETER,TYPE_USE})
public @interface MyAnnotation {
    String value()default "annotation";
}
