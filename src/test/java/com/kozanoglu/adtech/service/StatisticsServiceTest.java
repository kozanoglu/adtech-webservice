package com.kozanoglu.adtech.service;

import com.kozanoglu.adtech.dto.result.StatisticsGroupResultDTO;
import com.kozanoglu.adtech.dto.result.StatisticsResultDTO;
import com.kozanoglu.adtech.dto.result.Stats;
import com.kozanoglu.adtech.repository.DeliveryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private Query query;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    public void shouldFetchStatisticsBetweenExpectedDates() {
        // Given
        LocalDateTime startDate = LocalDateTime.of(2018, Month.AUGUST, 28, 19, 30, 0);
        LocalDateTime endDate = LocalDateTime.of(2018, Month.AUGUST, 29, 19, 30, 0);
        Date from = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        when(deliveryRepository.getDeliveryCountBetweenDates(from, to)).thenReturn(new Stats(3L, 2L, 1L));

        // When
        StatisticsResultDTO statisticsBetweenDates = statisticsService.getStatisticsBetweenDates(startDate, endDate);

        // Then
        assertThat(statisticsBetweenDates.getStats().getDeliveries()).isEqualTo(3L);
        assertThat(statisticsBetweenDates.getStats().getClicks()).isEqualTo(2L);
        assertThat(statisticsBetweenDates.getStats().getInstalls()).isEqualTo(1L);
        assertThat(statisticsBetweenDates.getInterval().getStart()).isEqualTo(from);
        assertThat(statisticsBetweenDates.getInterval().getEnd()).isEqualTo(to);

        // Verify
        verify(deliveryRepository).getDeliveryCountBetweenDates(from, to);
        verifyNoMoreInteractions(deliveryRepository, entityManager, query);
    }

    @Test
    public void shouldBuildSingleGroupingQueryAsExpected() {
        // Given
        LocalDateTime from = LocalDateTime.of(2018, Month.AUGUST, 29, 19, 30, 0);
        LocalDateTime to = LocalDateTime.now();
        String group = "browser";
        String[] groups = {group};

        List<Object[]> resultList = new ArrayList<>();
        Object[] record = {3L, 2L, 1L, "chrome"};
        resultList.add(record);

        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(resultList);

        // When
        StatisticsGroupResultDTO result = statisticsService.getGroupedStatisticsBetweenDates(from, to, groups);

        // Then
        assertThat(result.getData()).hasSize(1);
        Stats stats = result.getData().get(0).getStats();
        assertThat(stats).isNotNull();
        assertThat(stats.getDeliveries()).isEqualTo(3L);
        assertThat(stats.getClicks()).isEqualTo(2L);
        assertThat(stats.getInstalls()).isEqualTo(1L);
        Map<String, String> fields = result.getData().get(0).getFields();
        assertThat(fields.get("browser")).isEqualTo("chrome");
        assertThat(fields.get("os")).isNull();

        // Verify
        String expectedQuery = "SELECT count(distinct de), count(distinct cl), count(distinct ins), de.browser" +
                " FROM Delivery de" +
                " LEFT JOIN de.clicks cl" +
                " LEFT JOIN cl.installs ins" +
                " WHERE de.time between :startDate and :endDate" +
                " GROUP BY de.browser";
        verify(entityManager).createQuery(expectedQuery);
        verify(query).setParameter(eq("startDate"), any(Date.class));
        verify(query).setParameter(eq("endDate"), any(Date.class));
        verify(query).getResultList();
        verifyNoMoreInteractions(deliveryRepository, entityManager, query);
    }

    @Test
    public void shouldBuildMultiGroupingQueryAsExpected() {
        // Given
        LocalDateTime from = LocalDateTime.of(2018, Month.AUGUST, 29, 19, 30, 0);
        LocalDateTime to = LocalDateTime.now();
        String group1 = "browser";
        String group2 = "os";
        String[] groups = {group1, group2};

        List<Object[]> resultList = new ArrayList<>();
        Object[] record = {3L, 2L, 1L, "chrome", "iOS"};
        resultList.add(record);

        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(resultList);

        // When
        StatisticsGroupResultDTO result = statisticsService.getGroupedStatisticsBetweenDates(from, to, groups);

        // Then
        assertThat(result.getData()).hasSize(1);
        Stats stats = result.getData().get(0).getStats();
        assertThat(stats).isNotNull();
        assertThat(stats.getDeliveries()).isEqualTo(3L);
        assertThat(stats.getClicks()).isEqualTo(2L);
        assertThat(stats.getInstalls()).isEqualTo(1L);
        Map<String, String> fields = result.getData().get(0).getFields();
        assertThat(fields.get("os")).isEqualTo("iOS");
        assertThat(fields.get("browser")).isEqualTo("chrome");

        // Verify
        String expectedQuery = "SELECT count(distinct de), count(distinct cl), count(distinct ins), de.browser, de.os" +
                " FROM Delivery de" +
                " LEFT JOIN de.clicks cl" +
                " LEFT JOIN cl.installs ins" +
                " WHERE de.time between :startDate and :endDate" +
                " GROUP BY de.browser, de.os";
        verify(entityManager).createQuery(expectedQuery);
        verify(query).setParameter(eq("startDate"), any(Date.class));
        verify(query).setParameter(eq("endDate"), any(Date.class));
        verify(query).getResultList();
        verifyNoMoreInteractions(deliveryRepository, entityManager, query);
    }
}
