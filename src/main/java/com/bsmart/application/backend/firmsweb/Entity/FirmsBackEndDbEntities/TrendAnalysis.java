package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Enums.AccountSheetType;

import javax.persistence.*;

@Entity
@Table(name = "trendanalysis")
public class TrendAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private Integer year;
    private String sectorCode;
    private String accountSheetCode;

    @Enumerated(EnumType.STRING)
    private AccountSheetType accountType;
    private double value;

    public TrendAnalysis(Integer year, String sectorCode, String accountSheetCode, AccountSheetType accountType, double value) {

        this.year = year;
        this.sectorCode = sectorCode;
        this.accountSheetCode = accountSheetCode;
        this.accountType = accountType;
        this.value = value;
    }

    public TrendAnalysis() {
    }

    public String getAccountSheetTypeHumanized() {
        String accountSheetTypeHumanized = "";
        switch (this.accountType) {
            case BALANCESHEET:
                accountSheetTypeHumanized = "Bilanço";
                break;
            case INCOMESHEET:
                accountSheetTypeHumanized = "Gelir Tablosu";
                break;
            case CASHFLOWSHEET:
                accountSheetTypeHumanized = "Nakit Akım Tablosu";
                break;
            case FUNDFLOWSHEET:
                accountSheetTypeHumanized = "Fon Akım Tablosu";
                break;
            case VERTICALANALYSISBALANCESHEET:
                accountSheetTypeHumanized = "Bilanço Dikey Analiz Tablosu";
                break;
            case VERTICALANALYSISINCOMESHEET:
                accountSheetTypeHumanized = "Gelir Tablosu Dikey Analiz Tablosu";
                break;
            case HORIZONTALANALYSISBALANCESHEET:
                accountSheetTypeHumanized = "Bilanço Yatay Analiz Tablosu";
                break;
            case HORIZONTALANALYSISINCOMESHEET:
                accountSheetTypeHumanized = "Gelir Tablosu Yatay Analiz Tablosu";
                break;
            case RATIOSHEET:
                accountSheetTypeHumanized = "Rasyo Tablosu";
                break;
            default:
                accountSheetTypeHumanized = "Bilanço";
                break;
        }
        return accountSheetTypeHumanized;
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

    public String getAccountSheetCode() {
        return accountSheetCode;
    }

    public void setAccountSheetCode(String accountSheetCode) {
        this.accountSheetCode = accountSheetCode;
    }

    public AccountSheetType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountSheetType accountType) {
        this.accountType = accountType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
