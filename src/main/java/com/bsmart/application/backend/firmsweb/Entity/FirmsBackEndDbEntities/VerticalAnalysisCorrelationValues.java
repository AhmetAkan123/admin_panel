package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;

import javax.persistence.*;

@Entity
@Table(name = "verticalanalysiscorrelationvalues")
public class VerticalAnalysisCorrelationValues {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private Integer year;
    private String code;
    private String sectorCode;
    @Column(precision = 10, scale = 2)
    private double value;

    public VerticalAnalysisCorrelationValues(Integer year, String code, String sectorCode, double value) {

        this.year = year;
        this.code = code;
        this.sectorCode = sectorCode;
        this.value = value;
    }

    public VerticalAnalysisCorrelationValues() {

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
