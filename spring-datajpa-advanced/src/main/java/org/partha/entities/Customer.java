package org.partha.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.partha.dto.CustomerNetworthDto;

import java.util.List;


@Entity

@Table(name = "customer",schema = "mydatabase")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;

	@OneToMany(fetch=FetchType.LAZY ,cascade=CascadeType.ALL, mappedBy="customer")
	@JsonManagedReference //this is prevent circular serialization
	private List<Account> accounts;


}
