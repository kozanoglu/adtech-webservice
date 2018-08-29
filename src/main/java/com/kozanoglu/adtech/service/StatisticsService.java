package com.kozanoglu.adtech.service;

import com.kozanoglu.adtech.dto.result.*;
import com.kozanoglu.adtech.repository.DeliveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsService.class);

    private final DeliveryRepository deliveryRepository;
    private EntityManager entityManager;

    public StatisticsService(final DeliveryRepository deliveryRepository, final EntityManager entityManager) {
        this.deliveryRepository = deliveryRepository;
        this.entityManager = entityManager;
    }

    public StatisticsResultDTO getStatisticsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        Date from = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
        Stats stats = deliveryRepository.getDeliveryCountBetweenDates(from, to);
        Interval interval = new Interval(from, to);
        return new StatisticsResultDTO(interval, stats);
    }

    public StatisticsGroupResultDTO getGroupedStatisticsBetweenDates(LocalDateTime startDate, LocalDateTime endDate, String[] groupClauses) {
        try {
            Date from = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
            Date to = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

            String groupClause = Arrays.stream(groupClauses).map(t -> "de." + t).collect(Collectors.joining(", "));

            String queryStr = "SELECT count(distinct de), count(distinct cl), count(distinct ins), " + groupClause +
                    " FROM Delivery de" +
                    " LEFT JOIN de.clicks cl" +
                    " LEFT JOIN cl.installs ins" +
                    " WHERE de.time between :startDate and :endDate" +
                    " GROUP BY " + groupClause;

            Query query = entityManager.createQuery(queryStr);
            query.setParameter("startDate", from);
            query.setParameter("endDate", to);
            List<Object[]> list = query.getResultList();

            List<Data> dataList = new ArrayList<>();
            for (Object[] record : list) {
                Stats stats = new Stats((long) record[0], (long) record[1], (long) record[2]);

                Map<String, String> fields = new HashMap<>();
                for (int i = 3; i < record.length; i++) {
                    fields.put(groupClauses[i - 3], (String) record[i]);
                }
                dataList.add(new Data(fields, stats));
            }

            return new StatisticsGroupResultDTO(new Interval(from, to), dataList);
        } catch (Exception e) {
            LOG.error("Failed to fetch grouped statistics from DB", e);
            throw new RuntimeException(e);
        }
    }
}
