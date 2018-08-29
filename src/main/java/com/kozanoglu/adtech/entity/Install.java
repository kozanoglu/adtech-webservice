package com.kozanoglu.adtech.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Install {

    @Id
    @Column(unique = true)
    private String installId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "click_id")
    private Click click;

    private Date time;

    public String getInstallId() {
        return installId;
    }

    public void setInstallId(String installId) {
        this.installId = installId;
    }

    public Click getClick() {
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
