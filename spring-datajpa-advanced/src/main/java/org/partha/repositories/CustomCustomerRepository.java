package org.partha.repositories;

import org.partha.dto.CreateCustomerDto;

import java.util.List;

public interface CustomCustomerRepository {

    Integer insertCustomer(CreateCustomerDto dto);

//    List<Integer> incrementAgeAndRetrieve();

}
