package org.example.dto;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantDatabaseDto {

    private int id;
    private String name;
    private String url;
    private String username;
    private String password;
    private String driverClass;
}
