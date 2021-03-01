package com.anupama.sinha;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // Used only for field/method/type(class level) in main class
public @interface MyContext {

    @AliasFor("name") //To avoid writing "name" keyword in main class : @MyContext("Anupama")
    String name() default "";

    @AliasFor("age")
    int age() default 25;
}
