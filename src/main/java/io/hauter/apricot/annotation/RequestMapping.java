package io.hauter.apricot.annotation;

import io.hauter.apricot.model.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sun on 16/5/30.
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String urlPatterns() default "";

    RequestMethod [] method() default {};
}
