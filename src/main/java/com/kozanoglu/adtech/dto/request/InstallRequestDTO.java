package com.kozanoglu.adtech.dto.request;

import java.util.Date;

public class InstallRequestDTO {

    private String installId;
    private String clickId;
    private Date time;

    public InstallRequestDTO() {
    }

    public String getInstallId() {
        return installId;
    }

    public void setInstallId(String installId) {
        this.installId = installId;
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
