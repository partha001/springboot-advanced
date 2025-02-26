package org.partha.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts", schema = "mydatabase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference //this is prevent circular serialization
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="customerid")
    private Customer customer;

    @Column(name="type")
    private String type;

    @Column(name="balance")
    private Integer balance;

}
