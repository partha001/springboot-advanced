package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bond_payout_master")
public class BondInterestPayout {

    @Id
    private Integer id;

    @Column(name="payout_date")
    private Date payoutDate;

    @Column(name="isin")
    private String isin;

    @Column(name="listed")
    private String listed;

    @Column(name="issuer_name")
    private String issuerName;

    @Column(name="face_value")
    private Double faceValue;

    @Column(name="interest_amt")
    private Double interestAmt;

    @Column(name="total_amt")
    private Double totalAmt;

    @Column(name="remarks")
    private String remarks;

}
