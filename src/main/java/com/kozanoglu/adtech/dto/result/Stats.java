package com.kozanoglu.adtech.dto.result;

public class Stats {

    private long deliveries;
    private long clicks;
    private long installs;

    public Stats() {
    }

    public Stats(long deliveries, long clicks, long installs) {
        this.deliveries = deliveries;
        this.clicks = clicks;
        this.installs = installs;
    }

    public long getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(long deliveries) {
        this.deliveries = deliveries;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public long getInstalls() {
        return installs;
    }

    public void setInstalls(long installs) {
        this.installs = installs;
    }
}
