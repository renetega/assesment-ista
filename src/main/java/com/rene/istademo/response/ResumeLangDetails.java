package com.rene.istademo.response;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
public class ResumeLangDetails {

    private String name;

    private BigDecimal percentage;

    public ResumeLangDetails() {
    }

    public ResumeLangDetails(String name, BigDecimal percentage) {
        this.name = name;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
}
