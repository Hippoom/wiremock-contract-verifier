package com.github.hippoom.wiremock.contract.verifier.anntation;

import org.skyscreamer.jsonassert.JSONCompareMode;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(
    {
        METHOD
    }
)
public @interface Contract {

    String value();

    /**
     * set {@link JSONCompareMode#STRICT_ORDER}.
     * @return Boolean
     */
    boolean strictOrder() default false;

    /**
     * set {@link JSONCompareMode#NON_EXTENSIBLE}.
     * @return Boolean
     */
    boolean extensible() default true;
}
