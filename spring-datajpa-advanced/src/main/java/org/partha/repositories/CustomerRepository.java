package org.partha.repositories;

import org.partha.dto.CustomerNetworthDto;
import org.partha.dto.NetworthDto;
import org.partha.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    //making use of findBy<AttributeName> to filter on any column i.e. filtering with name as in this case
    //similarly we can do findby on any desired attribute that we want
    List<Customer> findByName(String name);


    //using method naming convention and 'AND' logical operator
    //similarly other logical operators like OR can also be used
    List<Customer> findByNameAndEmail(String name, String email);

    List<Customer> findByNameStartingWith(String startText);

    List<Customer> findByNameEndingWith(String endText);

    //List<Customer> findAllByNameLike(String likeText);

    List<Customer> findByNameContaining(String containText);

    List<Customer> findByAgeLessThan(Integer age);

    List<Customer> findByAgeLessThanEqual(Integer age);

    List<Customer> findByAgeIn(List<Integer> ageIn);

    List<Customer> findByAgeInOrderByNameDesc(List<Integer> ageIn);

    /**
     * the query annotation can take either JPQL(similar to HQL) or native sql
     * example of @Query with JPQL. ie we have to use entityName and attributeNames in the query
     */
    @Query("select c from Customer c where c.email=:e")
    List<Customer> getCustomerByEmail(@Param("e") String email);


    /**
     * @Query annotation with native query example
     */
    @Query(value="select * from customer where customer.email=:e",nativeQuery = true)
    List<Customer> getCustomerWithEmailUsingNativeQuery(@Param("e") String email);


    /**
     * DTO projections are of 2 types. 1.interface based dto projections and 2.class based dto projections
     * to load native query result into a DTO we have to define a DTO interface.
     * this is called interface based dto projection.
     * @return
     */
    @Query(value="select temp.customerid as id, c.name, c.email, temp.networth  from \n" +
            "(select a.customerid, sum(balance) as networth from accounts a\n" +
            "group by a.customerid) temp\n" +
            "left outer join \n" +
            "customer c\n" +
            "on temp.customerid = c.id", nativeQuery=true)
    List<NetworthDto> getAllNetworth();


//    /**
//     * example of class based dto projection
//     * @return
//     */
//    @Query(value="select new org.partha.dto.CustomerNetworthDto(temp.customerid as id, c.name as name, c.email as email, temp.networth as networth) from "+
//    //@Query(value="select temp.customerid as id, c.name as name, c.email as email, temp.networth as networth from "+
//            "(select a.customerid, sum(balance) as networth from accounts a  " +
//            "group by a.customerid) temp  " +
//            "left outer join " +
//            "customer c " +
//            "on temp.customerid = c.id", nativeQuery = true)
//    List<CustomerNetworthDto> getAllCustomerNetworth();


//    /**
//     * example of class based dto projection with parameterized query
//     */
//    @Query(value="select new org.partha.dto.CustomerNetworthDto(temp.customerid as id, c.name, c.email, temp.networth) from \n"+
//            "(select a.customerid, sum(balance) as networth from accounts a\n" +
//            "group by a.customerid) temp\n" +
//            "left outer join \n" +
//            "customer c\n" +
//            "on temp.customerid = c.id where c.email=:e", nativeQuery=true)
//    List<CustomerNetworthDto> findAllNetWorthByEmail(@Param("e") String email);







}
