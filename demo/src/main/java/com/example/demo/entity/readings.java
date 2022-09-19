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
public class readings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pumpid;
    private Long opening;
    private Long closing;
    private Long testing;
    private Double price;
    private Double netsale;
    private Double amount;
    private LocalDate date;
    private Long cid;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "readings{" +
                "id=" + id +
                ", pumpid=" + pumpid +
                ", opening=" + opening +
                ", closing=" + closing +
                ", testing=" + testing +
                ", price=" + price +
                ", netsale=" + netsale +
                ", amount=" + amount +
                ", date=" + date +
                ", cid=" + cid +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPumpid() {
        return pumpid;
    }

    public void setPumpid(Long pumpid) {
        this.pumpid = pumpid;
    }

    public Long getOpening() {
        return opening;
    }

    public void setOpening(Long opening) {
        this.opening = opening;
    }

    public Long getClosing() {
        return closing;
    }

    public void setClosing(Long closing) {
        this.closing = closing;
    }

    public Long getTesting() {
        return testing;
    }

    public void setTesting(Long testing) {
        this.testing = testing;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNetsale() {
        return netsale;
    }

    public void setNetsale(Double netsale) {
        this.netsale = netsale;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
