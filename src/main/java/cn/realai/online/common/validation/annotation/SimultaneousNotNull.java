package cn.realai.online.common.validation.annotation;

import cn.realai.online.common.validation.impl.SimultaneousNotNullImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SimultaneousNotNullImpl.class )
@Documented
public @interface  SimultaneousNotNull {

    int checkCount();

    String message() default "参数不能同时为空!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
