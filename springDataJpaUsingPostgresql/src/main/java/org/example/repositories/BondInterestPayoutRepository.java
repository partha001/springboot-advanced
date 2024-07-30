package org.example.repositories;

import jakarta.persistence.NamedNativeQuery;
import org.example.entities.BondInterestPayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface BondInterestPayoutRepository extends JpaRepository<BondInterestPayout,Integer> {

    @Query(value = "select sum(face_value) "+
            "from ( "
            +"select * from public.bond_payout_master "
            +"where payout_date <= :toDate "
            + ") ", nativeQuery = true)
    double getPrincipalPaidTillDate(Date toDate);

}
