package org.example.teaappbackend.aspect;

import org.example.teaappbackend.aspect.ejectors.KeyEjector;
import org.example.teaappbackend.aspect.mappers.DefaultValueMapper;
import org.example.teaappbackend.aspect.mappers.ValueMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logged {
    //todo: подумать по какому принципу абстрагировать любой вариант
    Class<? extends KeyEjector> keyEjector();
    Class<? extends ValueMapper> inputArgsMapper() default DefaultValueMapper.class;
    Class<? extends ValueMapper> outputArgsMapper() default DefaultValueMapper.class;
}
