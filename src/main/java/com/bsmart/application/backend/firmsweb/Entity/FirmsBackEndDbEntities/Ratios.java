package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Enums.AccountSheetTermType;

import javax.persistence.*;

@Entity
@Table(name = "ratios")
public class Ratios {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private Integer year;
    private String sectorCode;
    private String code;
    @Enumerated(EnumType.STRING)
    private AccountSheetTermType termType;
    private double value;

    public Ratios() {
        // Lazy İnit.
    }

    public Ratios(Integer year, String sectorCode, String code, double value) {
        this.year = year;
        this.sectorCode = sectorCode;
        this.code = code;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
