package com.hunk.java8;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, TYPE, FIELD, PARAMETER,CONSTRUCTOR,LOCAL_VARIABLE,ANNOTATION_TYPE,PACKAGE,TYPE_PARAMETER,TYPE_USE})
public @interface MyAnnotations {
    MyAnnotation[] value();
}
