package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Enums.AccountSheetTermType;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Enums.AccountSheetType;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by SedatKocadogan on 2.7.2017.
 */
@Entity
@Table(name = "accountsheets")
public class AccountSheets {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private AccountSheetType type;

    private int year;

    @Enumerated(EnumType.STRING)
    private AccountSheetTermType term;

    private Blob document;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "firm_id", nullable = false)
    private Firms firm;

    public AccountSheets() {
    }

    public AccountSheets(AccountSheetType type, int year, AccountSheetTermType term, Firms firm) {
        this.type = type;
        this.year = year;
        this.term = term;
        this.firm = firm;
    }

    public Firms getFirm() {
        return firm;
    }

    public void setFirm(Firms firm) {
        this.firm = firm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountSheetType getType() {
        return type;
    }

    public void setType(AccountSheetType type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public AccountSheetTermType getTerm() {
        return term;
    }

    public void setTerm(AccountSheetTermType term) {
        this.term = term;
    }

    public Blob getDocument() {
        return document;
    }

    public void setDocument(Blob document) {
        this.document = document;
    }
}
