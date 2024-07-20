package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.controllers;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.advices.GlobalExceptionHandler;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("create")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO employee){
       EmployeeDTO employeeDTO=employeeService.createEmployee(employee);
       return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
       return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping(path ="{id}")
    public ResponseEntity<EmployeeDTO> employeeById(@PathVariable(name = "id") Long EmployeeID){
    Optional<EmployeeDTO> employeeDTO=employeeService.employeeById(EmployeeID);

    return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok().body(employeeDTO1)).
            orElseThrow(() ->new ResourceNotFoundException("employee not found!"));

    }
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable(name = "id") Long employeeID){
        Boolean employeeDTOExist = employeeService.deleteEmployee(employeeID);
       if(employeeDTOExist)
           return ResponseEntity.ok(true);
       return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employee,@PathVariable Long id){

        EmployeeDTO employeeDTO=employeeService.updateEmployee(employee,id);
       return  ResponseEntity.ok(employeeDTO);
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<EmployeeDTO> patchEmployee(@RequestBody Map<String,Object> updates, @PathVariable Long id){
        EmployeeDTO employeeDTO = employeeService.partialUpdate(id,updates);
        if (employeeDTO==null)
             return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);


    }

}
