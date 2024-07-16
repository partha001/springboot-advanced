package org.partha.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.partha.dto.CustomerNetworthDto;

import java.util.List;


@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;

    @OneToMany(fetch=FetchType.LAZY ,cascade=CascadeType.ALL, mappedBy="account")
    private List<Account> account;


//	@OneToMany(fetch=FetchType.LAZY ,cascade=CascadeType.ALL, mappedBy="customer")
//	//@JsonManagedReference
//	private List<Account> accounts;


}
