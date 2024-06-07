package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j;

@Data
@Builder
public class Employee {

    private int id;
    private String name;
}
