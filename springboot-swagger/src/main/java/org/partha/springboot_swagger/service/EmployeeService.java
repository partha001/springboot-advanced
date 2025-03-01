package org.partha.springboot_swagger.service;


import org.partha.springboot_swagger.dto.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    int id=0;

     List<Employee> list = new ArrayList<>();

     public List<Employee> getAll(){
         return list;
     }

     public Employee findById(int id){
         Optional<Employee> result = list.stream().filter(employee -> employee.getId() == id).findFirst();
         return result.orElseGet(()-> {
             return null;
         });
     }

     public Employee createEmployee(String name){
         Employee newEmployee = Employee.builder()
                 .id(++id)
                 .name(name)
                 .build();
         list.add(newEmployee);
         return newEmployee;
     }


}

