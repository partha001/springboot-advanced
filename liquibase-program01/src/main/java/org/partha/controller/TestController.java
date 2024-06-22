package org.partha.controller;

import lombok.extern.slf4j.Slf4j;
import org.partha.entity.User;
import org.partha.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return new ResponseEntity<String>("Ping received and pinging back.", HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        log.info("inside TestController.getAllUsers()");
        List<User> userList = new ArrayList<User>();
        userRepository.findAll().iterator().forEachRemaining(item-> userList.add(item));
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findByUsername(@PathVariable("id") Integer id){
        return new ResponseEntity(userRepository.findById(id), HttpStatus.OK);
    }

}
