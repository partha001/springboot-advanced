package com.partha.read_write_database.repositories;

import com.partha.read_write_database.dbrouting.DataSourceSelector;
import com.partha.read_write_database.dbrouting.DataSources;
import com.partha.read_write_database.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    @DataSourceSelector
    List<Purchase> findByIdLessThan(long id);

    @DataSourceSelector(DataSources.READONLY)
    List<Purchase> findByIdGreaterThan(long id);

}
