package org.example.controller;

import org.example.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class MyController {

    @Autowired
    MyService myService;

    @GetMapping(value = "/test")
    public ResponseEntity test(){
        myService.test();
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping(value = "/executeQuery")
    public ResponseEntity executeQuery() throws SQLException {
        myService.executeQuery();
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping(value = "/cancelQuery")
    public ResponseEntity cancelQuery() throws SQLException {
        myService.cancelQuery();
        return new ResponseEntity(HttpStatus.OK);
    }



}
