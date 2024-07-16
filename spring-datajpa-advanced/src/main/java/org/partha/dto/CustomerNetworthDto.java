package org.partha.dto;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@NamedNativeQuery(name = "findAllNetWorth",
//        query = "select temp.customerid as id, c.name, c.email, temp.networth  from \n" +
//                "(select a.customerid, sum(balance) as networth from accounts a\n" +
//                "group by a.customerid) temp\n" +
//                "left outer join \n" +
//                "customer c\n" +
//                "on temp.customerid = c.id")
//@SqlResultSetMapping(
//        name = "findAllNetWorth",
//        classes = @ConstructorResult(
//                targetClass = CustomerNetworthDto.class,
//                columns = {
//                        @ColumnResult(name = "id"),
//                        @ColumnResult(name = "name"),
//                        @ColumnResult(name = "networth")
//                }
//        )
//)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerNetworthDto {

    private int id;
    private String name;
    private String email;
    private int networth;
}
