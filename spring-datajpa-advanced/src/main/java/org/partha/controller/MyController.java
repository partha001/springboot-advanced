package org.partha.controller;

import lombok.extern.log4j.Log4j;
import org.partha.dto.CustomerNetworthDto;
import org.partha.dto.NetworthDto;
import org.partha.entities.Customer;
import org.partha.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private MyService myService;


    @GetMapping(value = "/customers")
    public List<Customer> getAllCustomers(){
        return myService.getAllCustomers();
    }

    @GetMapping(value = "/customers/{customerId}")
    public Customer getAllCustomers(@PathVariable(name = "customerId") Integer customerId){
        return myService.findById(customerId);
    }

    @GetMapping(value = "/customers/name/{customerName}")
    public List<Customer> getAllCustomers(@PathVariable(name = "customerName") String name){
        return myService.findByName(name);
    }


    @GetMapping(value = "/customers/findByNameAndEmail")
    public List<Customer> findByNameAndEmail(@RequestParam("name") String name, @RequestParam("email") String email){
        return myService.findByNameAndEmail(name, email);
    }

    @GetMapping(value = "/customers/nameStartsWith/{startText}")
    public List<Customer> findByNameStartingWith(@PathVariable(name = "startText") String startText){
        return myService.findByNameStartingWith(startText);
    }

    @GetMapping(value = "/customers/nameEndsWith/{endText}")
    public List<Customer> findByNameEndingWith(@PathVariable(name = "endText") String endText){
        return myService.findByNameEndingWith(endText);
    }

//    @GetMapping(value = "/customers/nameLike/{likeText}")
//    public List<Customer> findByNameLike(@PathVariable(name = "likeText") String likeText){
//        return myService.findByNameLike(likeText);
//    }

    @GetMapping(value = "/customers/nameContaining/{containText}")
    public List<Customer> findByNameContaining(@PathVariable(name = "containText") String containText){
        return myService.findByNameContaining(containText);
    }


    @GetMapping(value = "/customers/ageLessThan/{age}")
    public List<Customer> findByAgeLessThan(@PathVariable(name = "age") Integer age){
        return myService.findByAgeLessThan(age);
    }

    @GetMapping(value = "/customers/ageLessThanEqual/{age}")
    public List<Customer> ageLessThanEqual(@PathVariable(name = "age") Integer age){
        return myService.ageLessThanEqual(age);
    }

    @PostMapping(value = "/customers/ageIn")
    public List<Customer> ageIn(@RequestBody  List<Integer> ageIn){
        return myService.ageIn(ageIn);
    }


    @PostMapping(value = "/customers/ageInOrderByNameDesc")
    public List<Customer> ageInOrderByNameDesc(@RequestBody  List<Integer> ageIn){
        return myService.ageInOrderByNameDesc(ageIn);
    }


    @GetMapping(value = "/customers/getByEmailUsingJpql/{email}")
    public List<Customer> getByEmailUsingJpql(@PathVariable(name = "email") String email){
        return myService.getByEmailUsingJpql(email);
    }

    @GetMapping(value = "/customers/getByEmailUsingNativeSql/{email}")
    public List<Customer> getByEmailUsingNativeSql(@PathVariable(name = "email") String email){
        return myService.getByEmailUsingNativeSql(email);
    }


    @GetMapping(value = "/customers/getAllNetworth")
    public List<NetworthDto> getAllNetworth(){
        return myService.getAllNetworth();
    }

    @GetMapping(value = "/creditBonus")
    public ResponseEntity<String> creditBonus(@RequestParam Integer bonus){
        myService.creditBonus(bonus);
        return new ResponseEntity<String>("bonus credited", HttpStatus.OK);
    }

    @GetMapping(value="/getCustomersByPage")
    public ResponseEntity<Page<Customer>> pageResult(@RequestParam int pageNumber, @RequestParam int pageSize){
        return new ResponseEntity<>(myService.findAllByPage(pageNumber, pageSize), HttpStatus.OK);
    }

//    @GetMapping(value = "/customers/getAllCustomerNetworth")
//    public List<CustomerNetworthDto> getAllCustomerNetworth(){
//        return myService.getAllCustomerNetworth();
//    }
//
//    @GetMapping(value = "/customers/getAllCustomerNetworthByEmail/{email}")
//    public List<CustomerNetworthDto> getAllCustomerNetworthByEmail(@PathVariable(name = "email") String email){
//        return myService.getAllCustomerNetworthByEmail(email);
//    }






//    @GetMapping(value = "/getCustomersWithAgeGreaterThanForty")
//    public List<Customer> getCustomersWithAgeGreaterThanForty(){
//        return myService.getCustomersWithAgeGreaterThanForty();
//    }


}
