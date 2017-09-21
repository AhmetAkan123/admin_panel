package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;

import javax.persistence.*;

/**
 * Created by BSmartPC01 on 10.07.2017.
 */
@Entity
@Table(name = "financialrisks")
public class FinancialRisks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private Integer year;
    private String sectorCode;
    private double value;

    public FinancialRisks(Integer year, String sectorCode, double value) {

        this.year = year;
        this.sectorCode = sectorCode;
        this.value = value;
    }

    public FinancialRisks() {

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
