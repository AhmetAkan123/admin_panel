package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;

import javax.persistence.*;

/**
 * Created by BSmartPC01 on 10.07.2017.
 */
@Entity
@Table(name = "g20values")
public class G20Values {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private Integer year;
    private double value;

    public G20Values(Integer year, double value) {

        this.year = year;
        this.value = value;
    }

    public G20Values() {

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
