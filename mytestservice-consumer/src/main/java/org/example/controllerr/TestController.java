package org.example.controllerr;

import org.example.controller.MyRestController;
import org.example.dto.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    MyRestController myRestController;

    public TestController(MyRestController myRestController){
        this.myRestController = myRestController;
    }

    @GetMapping(value= "/ping")
    public String ping(){
        return myRestController.ping();
    }


    @GetMapping(value="/employees/{empid}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer empid) {
        return myRestController.getEmployee(empid);
    }
}
