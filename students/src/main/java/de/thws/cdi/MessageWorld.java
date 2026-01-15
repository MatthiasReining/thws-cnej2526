package de.thws.cdi;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.inject.Qualifier;

@Qualifier
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD,
        java.lang.annotation.ElementType.METHOD,
        java.lang.annotation.ElementType.PARAMETER })
public @interface MessageWorld {

}
