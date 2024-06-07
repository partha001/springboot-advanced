package org.example.controller;

import org.example.dto.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mytestservice-client", url = "${mytestservice.url}")
public interface MyRestController {

    @GetMapping(value="/ping")
    String ping();

    @GetMapping(value="/employees/{empid}")
    ResponseEntity<Employee> getEmployee(@PathVariable Integer empid);

}
