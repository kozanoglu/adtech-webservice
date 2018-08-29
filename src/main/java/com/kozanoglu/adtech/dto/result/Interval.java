package com.kozanoglu.adtech.dto.result;

import java.util.Date;

public class Interval {

    private Date start;
    private Date end;

    public Interval() {
    }

    public Interval(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
