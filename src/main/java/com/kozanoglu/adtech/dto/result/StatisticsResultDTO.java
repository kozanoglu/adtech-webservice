package com.kozanoglu.adtech.dto.result;

public class StatisticsResultDTO extends ResultDTO {

    private Stats stats;

    public StatisticsResultDTO() {
    }

    public StatisticsResultDTO(Interval interval, Stats stats) {
        super(interval);
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
