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
public class engineoils {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Long qtyleft;
    private Long sales;
    private Long purchase;
    private Long rate;
    private Long amount;
    private Long eid;
    private Long cid;

    @Override
    public String toString() {
        return "engineoils{" +
                "id=" + id +
                ", date=" + date +
                ", qtyleft=" + qtyleft +
                ", sales=" + sales +
                ", purchase=" + purchase +
                ", rate=" + rate +
                ", amount=" + amount +
                ", eid=" + eid +
                ", cid=" + cid +
                '}';
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }
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

    public Long getQtyleft() {
        return qtyleft;
    }

    public void setQtyleft(Long qtyleft) {
        this.qtyleft = qtyleft;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Long getPurchase() {
        return purchase;
    }

    public void setPurchase(Long purchase) {
        this.purchase = purchase;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

}
