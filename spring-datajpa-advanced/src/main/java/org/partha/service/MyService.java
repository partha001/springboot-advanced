package org.partha.service;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.partha.dto.CustomerNetworthDto;
import org.partha.dto.NetworthDto;
import org.partha.entities.Customer;
import org.partha.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class MyService {

    @Autowired
    private CustomerRepository customerRepository;

    /** repository interface methods example starts here **/

    //making use of out of the box repository interface methods
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    //again making use of out of the box methods availabe in JpaRepository
    public Customer findById(Integer customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }


    /** derived repository method : example starts here **/
    //example of search on any generic column (as name is this case)
    public List<Customer> findByName(String name){
        return customerRepository.findByName(name);
    }


    /** derived repository method: using logical operator **/
    //example of search by different fields and AND logical operator
    public List<Customer> findByNameAndEmail(String name, String email) {
        return customerRepository.findByNameAndEmail(name,email);
    }

    /** derived repository method: example of <attributeName>StartingWith i.e search with prefix feature
     * provided by dataJPA**/
    //example of startingWith provided by spring data jpa
    public List<Customer> findByNameStartingWith(String startText) {
        return customerRepository.findByNameStartingWith(startText);
    }


    public List<Customer> findByNameEndingWith(String endText) {
        return customerRepository.findByNameEndingWith(endText);
    }

//    public List<Customer> findByNameLike(String likeText) {
//        return customerRepository.findByNameLike(Strings.concat("","",) likeText);
//    }

    public List<Customer> findByNameContaining(String containText) {
        return customerRepository.findByNameContaining(containText);
    }


    public List<Customer> findByAgeLessThan(Integer age) {
        return  customerRepository.findByAgeLessThan(age);
    }

    public List<Customer> ageLessThanEqual(Integer age) {
        return customerRepository.findByAgeLessThanEqual(age);
    }

    public List<Customer> ageIn(List<Integer> ageIn) {
        return customerRepository.findByAgeIn(ageIn);
    }

    public List<Customer> ageInOrderByNameDesc(List<Integer> ageIn) {
        return customerRepository.findByAgeInOrderByNameDesc(ageIn);
    }

    public List<Customer> getByEmailUsingJpql(String email) {
        return customerRepository.getCustomerByEmail(email);
    }

    public List<Customer> getByEmailUsingNativeSql(String email) {
        return customerRepository.getCustomerWithEmailUsingNativeQuery(email);
    }

    public List<NetworthDto> getAllNetworth() {
        return customerRepository.getAllNetworth();
    }

    public void creditBonus(int bonus) {
        int recordsUpdatad = customerRepository.creditBonus(bonus);
        log.info("total number of records upated:{}", recordsUpdatad);
    }


    public Page<Customer> findAllByPage(int pageNumber, int pageSize){
        Page<Customer> pageData = customerRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return pageData;
    }

//    public List<CustomerNetworthDto> getAllCustomerNetworth() {
//        return customerRepository.getAllCustomerNetworth();
//    }
//
//    public List<CustomerNetworthDto> getAllCustomerNetworthByEmail(String email) {
//        return customerRepository.findAllNetWorthByEmail(email);
//    }
}
