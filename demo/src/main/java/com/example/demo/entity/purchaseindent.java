package com.example.demo.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Controller
@Entity
public class purchaseindent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Long msTaxable;
    private Long msVat;
    private Long msSslf;
    private Long hsdTaxable;
    private Long hsdVat;
    private Long hsdSslf;
    private Long lubeTaxable;
    private Long lubeVat;
    private Long lubeSslf;
    private Long powerTaxable;
    private Long powerVat;
    private Long powerSslf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getMsTaxable() {
        return msTaxable;
    }

    public void setMsTaxable(Long msTaxable) {
        this.msTaxable = msTaxable;
    }

    public Long getMsVat() {
        return msVat;
    }

    public void setMsVat(Long msVat) {
        this.msVat = msVat;
    }

    public Long getMsSslf() {
        return msSslf;
    }

    public void setMsSslf(Long msSslf) {
        this.msSslf = msSslf;
    }

    public Long getHsdTaxable() {
        return hsdTaxable;
    }

    public void setHsdTaxable(Long hsdTaxable) {
        this.hsdTaxable = hsdTaxable;
    }

    public Long getHsdVat() {
        return hsdVat;
    }

    public void setHsdVat(Long hsdVat) {
        this.hsdVat = hsdVat;
    }

    public Long getHsdSslf() {
        return hsdSslf;
    }

    public void setHsdSslf(Long hsdSslf) {
        this.hsdSslf = hsdSslf;
    }

    public Long getLubeTaxable() {
        return lubeTaxable;
    }

    public void setLubeTaxable(Long lubeTaxable) {
        this.lubeTaxable = lubeTaxable;
    }

    public Long getLubeVat() {
        return lubeVat;
    }

    public void setLubeVat(Long lubeVat) {
        this.lubeVat = lubeVat;
    }

    public Long getLubeSslf() {
        return lubeSslf;
    }

    public void setLubeSslf(Long lubeSslf) {
        this.lubeSslf = lubeSslf;
    }

    public Long getPowerTaxable() {
        return powerTaxable;
    }

    public void setPowerTaxable(Long powerTaxable) {
        this.powerTaxable = powerTaxable;
    }

    public Long getPowerVat() {
        return powerVat;
    }

    public void setPowerVat(Long powerVat) {
        this.powerVat = powerVat;
    }

    public Long getPowerSslf() {
        return powerSslf;
    }

    public void setPowerSslf(Long powerSslf) {
        this.powerSslf = powerSslf;
    }

    @Override
    public String toString() {
        return "purchaseindent{" +
                "id=" + id +
                ", date=" + date +
                ", msTaxable=" + msTaxable +
                ", msVat=" + msVat +
                ", msSslf=" + msSslf +
                ", hsdTaxable=" + hsdTaxable +
                ", hsdVat=" + hsdVat +
                ", hsdSslf=" + hsdSslf +
                ", lubeTaxable=" + lubeTaxable +
                ", lubeVat=" + lubeVat +
                ", lubeSslf=" + lubeSslf +
                ", powerTaxable=" + powerTaxable +
                ", powerVat=" + powerVat +
                ", powerSslf=" + powerSslf +
                '}';
    }
}
