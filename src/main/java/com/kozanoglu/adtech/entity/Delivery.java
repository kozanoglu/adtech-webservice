package com.kozanoglu.adtech.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Delivery {

    @Id
    @Column(name = "delivery_id", unique = true)
    private String deliveryId;

    private long advertisementId;
    private Date time;
    private String browser;
    private String os;
    private String site;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "delivery")
    private List<Click> clicks = new ArrayList<>();

    public Delivery() {
    }

    public Delivery(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(long advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<Click> getClicks() {
        return clicks;
    }

    public void setClicks(List<Click> clicks) {
        this.clicks = clicks;
    }
}
