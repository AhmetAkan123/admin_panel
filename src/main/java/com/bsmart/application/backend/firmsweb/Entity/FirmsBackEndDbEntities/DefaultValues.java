package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Tablo Şablonları
 * Created by BSmartPC01 on 10.07.2017.
 */

@Entity
@Table(name = "defaultvalues")
public class DefaultValues {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private Blob BalanceSheet;
    private Blob IncomeSheet;
    private Blob CashFlowSheet;
    private Blob RatioSheet;
    private Blob FundFlowSheet;
    private Blob VerticalAnalysisBalanceSheet;
    private Blob VerticalAnalysisIncomeSheet;
    private Blob HorizontalAnalysisBalanceSheet;
    private Blob HorizontalAnalysisIncomeSheet;
    private Blob Dupont;

    public DefaultValues() {
        // Lazy Inıt...
    }

    public DefaultValues(Blob balanceSheet,
                         Blob incomeSheet,
                         Blob cashFlowSheet,
                         Blob ratioSheet,
                         Blob fundFlowSheet,
                         Blob verticalAnalysisBalanceSheet,
                         Blob verticalAnalysisIncomeSheet,
                         Blob horizontalAnalysisBalanceSheet,
                         Blob horizontalAnalysisIncomeSheet,
                         Blob dupont) {

        BalanceSheet = balanceSheet;
        IncomeSheet = incomeSheet;
        CashFlowSheet = cashFlowSheet;
        RatioSheet = ratioSheet;
        FundFlowSheet = fundFlowSheet;
        VerticalAnalysisBalanceSheet = verticalAnalysisBalanceSheet;
        VerticalAnalysisIncomeSheet = verticalAnalysisIncomeSheet;
        HorizontalAnalysisBalanceSheet = horizontalAnalysisBalanceSheet;
        HorizontalAnalysisIncomeSheet = horizontalAnalysisIncomeSheet;
        Dupont = dupont;
    }

    public Blob getDupont() {
        return Dupont;
    }

    public void setDupont(Blob dupont) {
        Dupont = dupont;
    }

    public Blob getFundFlowSheet() {
        return FundFlowSheet;
    }

    public void setFundFlowSheet(Blob fundFlowSheet) {
        FundFlowSheet = fundFlowSheet;
    }

    public Blob getRatioSheet() {
        return RatioSheet;
    }

    public void setRatioSheet(Blob ratioSheet) {
        RatioSheet = ratioSheet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Blob getBalanceSheet() {
        return BalanceSheet;
    }

    public void setBalanceSheet(Blob balanceSheet) {
        BalanceSheet = balanceSheet;
    }

    public Blob getIncomeSheet() {
        return IncomeSheet;
    }

    public void setIncomeSheet(Blob incomeSheet) {
        IncomeSheet = incomeSheet;
    }

    public Blob getCashFlowSheet() {
        return CashFlowSheet;
    }

    public void setCashFlowSheet(Blob cashFlowSheet) {
        CashFlowSheet = cashFlowSheet;
    }

    public Blob getVerticalAnalysisBalanceSheet() {
        return VerticalAnalysisBalanceSheet;
    }

    public void setVerticalAnalysisBalanceSheet(Blob verticalAnalysisBalanceSheet) {
        VerticalAnalysisBalanceSheet = verticalAnalysisBalanceSheet;
    }

    public Blob getVerticalAnalysisIncomeSheet() {
        return VerticalAnalysisIncomeSheet;
    }

    public void setVerticalAnalysisIncomeSheet(Blob verticalAnalysisIncomeSheet) {
        VerticalAnalysisIncomeSheet = verticalAnalysisIncomeSheet;
    }

    public Blob getHorizontalAnalysisBalanceSheet() {
        return HorizontalAnalysisBalanceSheet;
    }

    public void setHorizontalAnalysisBalanceSheet(Blob horizontalAnalysisBalanceSheet) {
        HorizontalAnalysisBalanceSheet = horizontalAnalysisBalanceSheet;
    }

    public Blob getHorizontalAnalysisIncomeSheet() {
        return HorizontalAnalysisIncomeSheet;
    }

    public void setHorizontalAnalysisIncomeSheet(Blob horizontalAnalysisIncomeSheet) {
        HorizontalAnalysisIncomeSheet = horizontalAnalysisIncomeSheet;
    }
}
