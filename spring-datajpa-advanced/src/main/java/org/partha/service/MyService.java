package org.partha.service;

import lombok.extern.log4j.Log4j2;
import org.partha.dto.CreateCustomerDto;
import org.partha.entities.Customer;
import org.partha.projections.NetworthProjection;
import org.partha.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
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

    public List<Customer> findByEmailContains(String containsText) {
        return customerRepository.findByEmailContains(containsText);
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

    public List<Customer> findByAgeBetween(int minage, int maxage){
        return customerRepository.findByAgeBetween(minage, maxage);
    }

    public List<Customer> getByEmailUsingJpql(String email) {
        return customerRepository.getCustomerByEmail(email);
    }

    public List<Customer> getByEmailUsingNativeSql(String email) {
        return customerRepository.getCustomerWithEmailUsingNativeQuery(email);
    }

    public List<NetworthProjection> getAllNetworth() {
        return customerRepository.getAllNetworth();
    }

    public void creditBonus(int bonus) {
        int recordsUpdatad = customerRepository.creditBonus(bonus);
        log.info("total number of records upated:{}", recordsUpdatad);
    }


    /**
     * 1. it is also to be noted that while responding back for a page-request we should always add sorting
     * else same data might appear in multiple pages . so we should add some default sorting even if its not asked
     *
     * 2. we request for the first page by passing pageNumber=0
     *
     * 3. since we are returning the page directly here and not the content so inspect the response to
     * better understand what are the page attributes that we get.
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<Customer> findAllByPage(int pageNumber, int pageSize){
        /** some other examples of making a page request**/
//        Pageable sortedByName =
//                PageRequest.of(0, 3, Sort.by("name"));
//
//        Pageable sortedByPriceDesc =
//                PageRequest.of(0, 3, Sort.by("price").descending());
//
//        Pageable sortedByPriceDescNameAsc =
//                PageRequest.of(0, 5, Sort.by("price").descending().and(Sort.by("name")));
//

        /** sorting should always be used even by default**/
        //Page<Customer> pageData = customerRepository.findAll(PageRequest.of(pageNumber, pageSize);

        Page<Customer> pageData = customerRepository.findAll(PageRequest.of(pageNumber, pageSize,Sort.by("id")));
        return pageData;

    }


    /**
     * note that its the same method as above . only difference is the return type.
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<Customer> findAllByPageData(int pageNumber, int pageSize) {
        Page<Customer> pageData = customerRepository.findAll(PageRequest.of(pageNumber, pageSize,Sort.by("id")));
        return pageData.getContent();
    }

    public void printAllCustomerPageWise() {
        Pageable pageRequest = PageRequest.of(0, 2, Sort.by("id"));
        Page<Customer> pageOfCustomers;


        do {
            pageOfCustomers = customerRepository.findAll(pageRequest);
            //do something with the page data here
            log.info("pageNumber:{} page", pageOfCustomers.getNumber());
            pageOfCustomers.getContent().forEach(customer -> log.info(customer.toString()));


            pageRequest = pageRequest.next();
        } while (pageOfCustomers.hasNext());
    }

    public Customer createCustomer(CreateCustomerDto dto) {
        Customer customer = Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .build();
        return customerRepository.save(customer);
    }

    public List<Customer> emailIsContaining(String containingText) {
        return customerRepository.findByEmailIsContaining(containingText);
    }

    public List<Customer> emailLike(String likeText) {
        return customerRepository.findByEmailLike("%"+likeText+"%");
    }

    public Integer insertCustomer(CreateCustomerDto dto) {
        return customerRepository.insertCustomer(dto);
    }




//    public List<Integer> incrementAgeAndRetrieve() {
//        return customerRepository.incrementAgeAndRetrieve();
//    }

//    public List<CustomerNetworthDto> getAllCustomerNetworth() {
//        return customerRepository.getAllCustomerNetworth();
//    }
//
//    public List<CustomerNetworthDto> getAllCustomerNetworthByEmail(String email) {
//        return customerRepository.findAllNetWorthByEmail(email);
//    }
}
