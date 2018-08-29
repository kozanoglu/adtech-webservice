package com.kozanoglu.adtech.dto.result;

import java.util.List;

public class StatisticsGroupResultDTO extends ResultDTO {

    private List<Data> data;

    public StatisticsGroupResultDTO() {
    }

    public StatisticsGroupResultDTO(Interval interval, List<Data> data) {
        super(interval);
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
