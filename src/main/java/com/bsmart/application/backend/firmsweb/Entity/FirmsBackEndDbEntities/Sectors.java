package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Enums.SectorType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sectors")
public class Sectors {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private String sectorCode;
    private String sectorName;

    @OneToMany(mappedBy = "firmsector", cascade = CascadeType.ALL)
    private List<Firms> firms;

    @Enumerated(EnumType.STRING)
    private SectorType sectorType;

    public Sectors() {
        // Lazy init
    }

    public Sectors(String sectorCode, String sectorName, SectorType sectorType) {
        this.sectorCode = sectorCode;
        this.sectorName = sectorName;
        this.sectorType = sectorType;
    }

    public List<Firms> getFirms() {
        return firms;
    }

    public void setFirms(List<Firms> firms) {
        this.firms = firms;
    }

    public SectorType getSectorType() {
        return sectorType;
    }

    public void setSectorType(SectorType sectorType) {
        this.sectorType = sectorType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSectorHumanizedType() {
        String sectorHumanizedType = "";
        switch (this.sectorType) {
            case PRODUCTION:
                sectorHumanizedType = "İmalat";
                break;
            case SERVICES:
                sectorHumanizedType = "İmalat Dışı";
                break;
            default:
                sectorHumanizedType = "İmalat";
                break;
        }

        return sectorHumanizedType;
    }

}
