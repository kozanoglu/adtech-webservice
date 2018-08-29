package com.kozanoglu.adtech.dto.request;

import java.util.Date;

public class ClickRequestDTO {

    private String deliveryId;
    private String clickId;
    private Date time;

    public ClickRequestDTO() {
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getClickId() {
        return clickId;
    }

    public void setClickId(String clickId) {
        this.clickId = clickId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}