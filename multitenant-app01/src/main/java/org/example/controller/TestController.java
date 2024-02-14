package org.example.controller;

import java.util.List;

import org.example.entities.Purchase;
import org.example.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {


    @Autowired
    PurchaseRepository purchaseRepository;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        log.info("TestController.ping():: tenantId:{}");
        return new ResponseEntity<String>("Ping received and pinging back.", HttpStatus.OK);
    }

    @GetMapping("/purchases")
    public ResponseEntity<List<Purchase>> getPurchases(){
        return new ResponseEntity<List<Purchase>>(purchaseRepository.findAll(), HttpStatus.OK);
    }
    
    
    @PostMapping("/purchases")
    public ResponseEntity insertPurchase(@RequestBody Purchase purchase) {
    	purchaseRepository.save(purchase);
    	return new ResponseEntity(HttpStatus.OK);
    }
}
