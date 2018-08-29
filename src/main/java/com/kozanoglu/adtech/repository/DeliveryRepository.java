package com.kozanoglu.adtech.repository;

import com.kozanoglu.adtech.dto.result.Stats;
import com.kozanoglu.adtech.entity.Delivery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface DeliveryRepository extends CrudRepository<Delivery, String> {

    @Query("SELECT new com.kozanoglu.adtech.dto.result.Stats(count(distinct de) as deliveries, count(distinct cl) as clicks, count(distinct ins) as installs) " +
            "FROM Delivery de " +
            "LEFT JOIN de.clicks cl " +
            "LEFT JOIN cl.installs ins " +
            "where de.time between :startDate and :endDate")
    Stats getDeliveryCountBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
