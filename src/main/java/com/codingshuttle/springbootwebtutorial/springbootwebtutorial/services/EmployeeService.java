package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.services;

import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.configs.MapperConfig;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class  EmployeeService {
    EmployeeRepository repository;
    MapperConfig mapperConfig;

    public EmployeeService(EmployeeRepository repository, MapperConfig mapperConfig) {
        this.repository = repository;
        this.mapperConfig = mapperConfig;
    }


    public Optional<EmployeeDTO> employeeById(Long employeeID) {

        return repository.findById(employeeID).
                map(employeeEntity -> mapperConfig.getMapper().map(employeeEntity, EmployeeDTO.class));

    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = repository.findAll();
       return  employeeEntities.stream()
                .map(employeeEntity -> mapperConfig.getMapper().map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        EmployeeEntity employeeEntity=mapperConfig.getMapper().map(employee, EmployeeEntity.class);
      //log staff
       EmployeeEntity savedEmployeeEntity= repository.save(employeeEntity);
       return mapperConfig.getMapper().map(savedEmployeeEntity, EmployeeDTO.class);

    }

    public  Boolean deleteEmployee(Long employeeID) {
      existsEmployeeById(employeeID);
            repository.deleteById(employeeID);
        return false;

    }

    public void existsEmployeeById(Long employeeID) {
        boolean isExist = repository.existsById(employeeID);
        if (!isExist) throw new ResourceNotFoundException("Employee with id " + employeeID + " does not exist");
    }
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO,Long employeeID) {
        existsEmployeeById(employeeID);
        employeeDTO.setId(employeeID);
        EmployeeEntity employeeEntity = mapperConfig.getMapper().map(employeeDTO, EmployeeEntity.class);
        repository.save(employeeEntity);
        return mapperConfig.getMapper().map(employeeEntity, EmployeeDTO.class);

    }



    public EmployeeDTO partialUpdate(Long id, Map<String, Object> updates) {
        existsEmployeeById(id);
        EmployeeEntity employeeEntity = repository.findById(id).get();
            updates.forEach((key, value) -> {
               Field filedTobeUpdated = ReflectionUtils.findField(EmployeeEntity.class, key);
                filedTobeUpdated.setAccessible(true);
                ReflectionUtils.setField(filedTobeUpdated, employeeEntity, value);


            });

            repository.save(employeeEntity);

           return mapperConfig.getMapper().map(employeeEntity, EmployeeDTO.class);

    }
}
