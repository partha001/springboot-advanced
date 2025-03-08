package com.partha.read_write_database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "public", name="purchase")
public class Purchase {

    @Id
    Long id;
    String name;
    String category;
}
