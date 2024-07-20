package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EmployeeRoleValidator implements ConstraintValidator <EmployeeRoleValidation,String>{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> roles=List.of("ADMIN","USER","HR","Accountant");
        return roles.contains(value);//
    }
}
