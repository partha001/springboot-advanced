package org.example.repositories;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.2.0")
            .withUsername("root")
            .withPassword("password");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username",mySQLContainer::getUsername);
        registry.add("spring.datasource.password",mySQLContainer::getPassword);

    }

    @BeforeAll
    static void beforeAll(){
        log.info("executing before all");
        mySQLContainer.start();
    }

    @AfterAll
    static void tearDown(){
        log.info("executing after all");
        mySQLContainer.stop();
    }

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
    }

    @Test
    void verifyInsert(){
        User user = User.builder()
                .username("testuser")
                .id(new Random().nextLong())
                .email("testuser@gmail.com")
                .firstName("testUserFirstName")
                .lastName("testUserLastName")
                .build();

        User saveUser = userRepository.save(user);
        assertEquals(user.getUsername(), saveUser.getUsername());
    }
}