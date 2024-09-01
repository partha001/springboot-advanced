package org.partha.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    Logger log = LogManager.getLogger(TestController.class);

    @GetMapping("/test")
    public String test(){
        log.info("TestController.test():: start");
        return "hello world";
    }

    @GetMapping("/testException")
    public String testException(){
        log.info("TestController.testException():: start");
        throw new RuntimeException("some exception");
    }

    @GetMapping("/testLoggedException")
    public String testLoggedException(){
        log.info("TestController.testLoggedException():: start");
        try{
            throw new RuntimeException("some exception");
        }catch(Exception e){
            log.error("some exception occurred", e);
        }
        return "exception occurred and logged";
    }


}
