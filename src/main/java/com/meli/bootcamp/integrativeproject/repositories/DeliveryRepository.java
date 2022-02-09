package com.meli.bootcamp.integrativeproject.repositories;

import com.meli.bootcamp.integrativeproject.entity.Delivery;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @Query(
            value = "select *  from DELIVERY D  INNER JOIN CARTS  C ON D.CART_ID = C.ID INNER JOIN BUYERS B  ON C.BUYER_ID = B.ID WHERE B.NAME = :name",
            nativeQuery = true
    )
    List<Delivery> find(String name);

    @Query(
            value = "SELECT * FROM DELIVERY WHERE BUYER_ID = :BUYER_ID AND CART_ID = :CART_ID",
            nativeQuery = true
    )
    List<Delivery> findany(Long BUYER_ID, Long CART_ID);
}