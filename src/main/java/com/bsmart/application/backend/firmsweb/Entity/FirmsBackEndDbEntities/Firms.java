package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;

import com.bsmart.application.backend.firmsweb.Entity.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "firmdata")
public class Firms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private String firmname;
    private String firmaddress = "";
    private String firmtelephone = "0";
    private String firmwebaddress = "http://";
    private String firmmanager = " ";
    private String firmTaxOffice = "";
    private String firmTexNumber = "";
    @Column(name = "visible")
    private boolean visible = true;

    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AccountSheets> sheets;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sectors firmsector;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Firms() {
    }

    public Firms(String firmname, Sectors firmSector) {
        this.firmname = firmname;
        this.firmsector = firmSector;
        this.firmaddress = "";
        this.firmmanager = "";
        this.visible = true;
    }

    public String getVisibleHumanized() {
        if (!visible) {
            return "Gizli";
        } else {
            return "Görünür";
        }
    }

    public Sectors getFirmsector() {
        return firmsector;
    }

    public void setFirmsector(Sectors firmsector) {
        this.firmsector = firmsector;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sectors getSector() {
        return firmsector;
    }

    public void setSector(Sectors firmSector) {
        this.firmsector = firmSector;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirmname() {
        return firmname;
    }

    public void setFirmname(String firmname) {
        this.firmname = firmname;
    }

    public List<AccountSheets> getSheets() {
        return sheets;
    }

    public void setSheets(List<AccountSheets> sheets) {
        this.sheets = sheets;
    }

    public String getFirmaddress() {
        return firmaddress;
    }

    public void setFirmaddress(String firmaddress) {
        this.firmaddress = firmaddress;
    }

    public String getFirmtelephone() {
        return firmtelephone;
    }

    public void setFirmtelephone(String firmtelephone) {
        this.firmtelephone = firmtelephone;
    }

    public String getFirmwebaddress() {
        return firmwebaddress;
    }

    public void setFirmwebaddress(String firmwebaddress) {
        this.firmwebaddress = firmwebaddress;
    }

    public String getFirmmanager() {
        return firmmanager;
    }

    public void setFirmmanager(String firmmanager) {
        this.firmmanager = firmmanager;
    }

    public String getFirmTaxOffice() {
        return firmTaxOffice;
    }

    public void setFirmTaxOffice(String firmTaxOffice) {
        this.firmTaxOffice = firmTaxOffice;
    }

    public String getFirmTexNumber() {
        return firmTexNumber;
    }

    public void setFirmTexNumber(String firmTexNumber) {
        this.firmTexNumber = firmTexNumber;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}