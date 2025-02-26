package org.partha.controller;

import org.partha.dto.CreateCustomerDto;
import org.partha.entities.Customer;
import org.partha.projections.NetworthProjection;
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


    @GetMapping(value ="/customers/emailContains/{containsText}")
    public List<Customer> findByEmailContains(@PathVariable(name = "containsText") String containsText){
        return myService.findByEmailContains(containsText);
    }

    @GetMapping(value = "/customers/nameContaining/{containText}")
    public List<Customer> findByNameContaining(@PathVariable(name = "containText") String containText){
        return myService.findByNameContaining(containText);
    }

    @GetMapping(value ="/customers/emailIsContaining/{containingText}")
    public List<Customer> emailIsContaining(@PathVariable(name = "containingText") String containingText){
        return myService.emailIsContaining(containingText);
    }


    @GetMapping(value ="/customers/emailLike/{likeText}")
    public List<Customer> emailLike(@PathVariable(name = "likeText") String likeText){
        return myService.emailLike(likeText);
    }

    @GetMapping(value = "/customers/ageLessThan/{age}")
    public List<Customer> findByAgeLessThan(@PathVariable(name = "age") Integer age){
        return myService.findByAgeLessThan(age);
    }

    @GetMapping(value = "/customers/ageLessThanEqual/{age}")
    public List<Customer> ageLessThanEqual(@PathVariable(name = "age") Integer age){
        return myService.ageLessThanEqual(age);
    }

    @PostMapping(value = "/customers/ageBetween")
    public List<Customer> ageInOrderByNameDesc(@RequestParam int minage, @RequestParam int maxage){
        return myService.findByAgeBetween(minage, maxage);
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
    public List<NetworthProjection> getAllNetworth(){
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

    @GetMapping(value="/getCustomersByPageData")
    public ResponseEntity<List<Customer>> pageResultData(@RequestParam int pageNumber, @RequestParam int pageSize){
        return new ResponseEntity<>(myService.findAllByPageData(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value="/printAllCustomerPageWise")
    public ResponseEntity printAllCustomerPageWise(){
        myService.printAllCustomerPageWise();
        return new ResponseEntity(HttpStatus.OK);
    }




    /**
     * note: that we dont pass a value for id . its generated by the application and is returned in the response
     * request payload:
     * {
     *     "name":"dravid",
     *     "email":"dravid@gmail.com",
     *     "age":30
     * }
     *
     * respose payload:
     *{
     *     "id": 52,
     *     "name": "dravid",
     *     "email": "dravid@gmail.com",
     *     "age": 30,
     *     "accounts": null
     * }
     * @param dto
     * @return
     */
    @PostMapping(value="/createCustomer")
    public ResponseEntity<Customer> createCustomer(@RequestBody CreateCustomerDto dto){
        return new ResponseEntity<Customer>(myService.createCustomer(dto), HttpStatus.OK);
    }


    /**
     * this is to explore keyHolder. keyHolders are used to get the record inserted or updated.
     * now in the previous case the repository save method creates the entity and returns the same upon
     * successful creation. however if want to do an update and get hold of the key of updatedRecord at the same
     * time where we dont know beforehand that which record will be updated the] then we will need the help of keyholder
     *
     * example: keyholder to get key of newly  inserted record
     */
    @PostMapping(value ="/insertCustomer")
    public ResponseEntity<Integer> insertCustomer(@RequestBody CreateCustomerDto dto){
        return new ResponseEntity<Integer>(myService.insertCustomer(dto), HttpStatus.OK);
    }

//    @GetMapping(value ="/incrementAgeAndRetrieve")
//    public ResponseEntity<List<Integer>> insertCustomer(){
//        return new ResponseEntity<List<Integer>>(myService.incrementAgeAndRetrieve(), HttpStatus.OK);
//    }











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
