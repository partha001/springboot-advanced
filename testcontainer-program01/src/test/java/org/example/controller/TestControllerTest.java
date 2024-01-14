package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    MockMvc mockMvc;

//    // we can initailize the container as below to go with default beharvious
//    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

    /** however since our dev database is using the default mysql port so can use the builder
     * pattern to configure our own customized properties as shown below
    **/
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

    @Test
    void verifyCreateUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User  user = User.builder()
                .email("testUser@gmail.com")
                .firstName("testUserFirstname")
                .lastName("testUserLastname")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(user))
                .characterEncoding("utf-8")
                .accept("application/json"))
                        //.andExpe
                .andExpect(status().isOk());
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

}