package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;

import javax.persistence.*;

@Entity
@Table(name = "horizontalratios")
public class HorizontalRatios {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private Integer year;
    private String sectorCode;
    private String code;
    private double change;
    private double difference;

    public HorizontalRatios(Integer year, String sectorCode, String code, double change, double difference) {
        this.year = year;
        this.sectorCode = sectorCode;
        this.code = code;
        this.change = change;
        this.difference = difference;
    }

    public HorizontalRatios() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }
}
