package org.example.controller;

import org.example.dto.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestCnontroller {

    @GetMapping(value="/ping")
    public String ping(){
        return "ping";
    }

    @GetMapping(value="/employees/{empid}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer empid){
        Employee emp =  Employee.builder()
                .id(empid)
                .name("partha")
                .build();
        return ResponseEntity.ok(emp);
    }
}
