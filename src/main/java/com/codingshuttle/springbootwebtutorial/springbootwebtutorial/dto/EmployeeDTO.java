package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto;


import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {

  private Long id;
  @NotNull(message = "Field {name} in EmployeeDto can't be null")
  @NotEmpty(message = "Field {name} can't be empty")
  @NotBlank(message = "Filed {name} can't be blank")
  @Size(min = 2,max = 10,message = "Field {name} size should be in range [2,10]")
  private String name;
  @Email(message = "Filed {Email} should have right pattern")
  private String email;
  @Max(value = 100,message = "Filed {age} can't be more than 100")
  private Integer age;
  private LocalDate dateOfJoining;
  @AssertFalse(message = "Employee should be inactive")
  private Boolean isActive;
//  @Pattern(regexp = "\\b(ADMIN|USER)\\b",message = "Filed {role} should be ADMIN or USER")
@EmployeeRoleValidation
  private String role;
//  @PositiveOrZero(message = "Filed [salary] can be zero or more")
  @DecimalMin(value = "100.2")
  @Digits(integer = 10,fraction = 2)
  private Double salary;




}
