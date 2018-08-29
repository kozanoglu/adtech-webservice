package com.kozanoglu.adtech.dto.result;

import java.util.Map;

public class Data {

    private Map<String, String> fields;
    private Stats stats;

    public Data() {
    }

    public Data(Map<String, String> fields, Stats stats) {
        this.fields = fields;
        this.stats = stats;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
