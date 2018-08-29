package com.kozanoglu.adtech.dto.result;

public abstract class ResultDTO {

    private Interval interval;

    public ResultDTO() {
    }

    public ResultDTO(Interval interval) {
        this.interval = interval;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }
}
