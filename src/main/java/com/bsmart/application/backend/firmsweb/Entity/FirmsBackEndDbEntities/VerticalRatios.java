package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Enums.AccountSheetTermType;

import javax.persistence.*;

@Entity
@Table(name = "verticalratios")
public class VerticalRatios {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private Integer year;
    private String code;
    private String sectorCode;
    @Enumerated(EnumType.STRING)
    private AccountSheetTermType termType;
    private double value;


    public VerticalRatios() {
    }

    public VerticalRatios(Integer year, String code, String sectorCode, double value) {
        this.year = year;
        this.code = code;
        this.sectorCode = sectorCode;
        this.value = value;
    }

    public AccountSheetTermType getTermType() {
        return termType;
    }

    public void setTermType(AccountSheetTermType termType) {
        this.termType = termType;
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
