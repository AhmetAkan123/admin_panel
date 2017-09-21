package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;

import javax.persistence.*;

@Entity
@Table(name = "grossdomesticproducts")
public class GrossDomesticProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private Integer year;
    private double value;

    public GrossDomesticProducts(Integer year, double value) {

        this.year = year;
        this.value = value;
    }

    public GrossDomesticProducts() {

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
