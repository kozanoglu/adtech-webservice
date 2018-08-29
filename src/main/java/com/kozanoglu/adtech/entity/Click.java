package com.kozanoglu.adtech.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity()
public class Click {

    @Id
    @Column(name = "click_id", unique = true)
    private String clickId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private Date time;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "click")
    private List<Install> installs = new ArrayList<>();

    public Click() {
    }

    public Click(String clickId) {
        this.clickId = clickId;
    }

    public String getClickId() {
        return clickId;
    }

    public void setClickId(String clickId) {
        this.clickId = clickId;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Install> getInstalls() {
        return installs;
    }

    public void setInstalls(List<Install> installs) {
        this.installs = installs;
    }
}
