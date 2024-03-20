package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="purchase")
public class Purchase {

    @Id
    Long id;

    String name;
    String category;
}
