package com.partha.read_write_database.controllers;

import com.partha.read_write_database.entities.Purchase;
import com.partha.read_write_database.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PurchaseController {


    @Autowired
    PurchaseRepository purchaseRepository;

    /**
     * Scenario1:  when dao method is not annotated. it should read from READWRITE database.
     * @return
     */
    @GetMapping("/getAllWithoutAnnotation")
    public ResponseEntity<List<Purchase>> getAll(){
        List<Purchase> all = purchaseRepository.findAll();
        return new ResponseEntity<List<Purchase>>(all, HttpStatus.OK);
    }

    /**
     * Scenario2: dao method is annotated but no annotation argument is passed. it should read from READ database
     * @return
     */
    @GetMapping("/getAllWithAnnotationDefault")
    public ResponseEntity<List<Purchase>> getAllReadOnlyPurchase(){
        List<Purchase> all = purchaseRepository.findByIdLessThan(100);
        return new ResponseEntity<List<Purchase>>(all, HttpStatus.OK);
    }


    /**
     * Scenario3: dao method is annotated with custom annotation and annotation argument is passed as READONLY.
     * hence it should read from READ-DATABASE
     * @return
     */
    @GetMapping("/getAllWithAnnotationWithSelectorReadOnly")
    public ResponseEntity<List<Purchase>> getAllWithAnnotationWithSelectorReadOnly(){
        List<Purchase> all = purchaseRepository.findByIdGreaterThan(0);
        return new ResponseEntity<List<Purchase>>(all, HttpStatus.OK);
    }
}
