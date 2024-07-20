package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.br.CNPJ;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = EmployeeRoleValidator.class)
public @interface EmployeeRoleValidation {

    String message() default "Filed Role can be only [USER,ADMIN]";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
