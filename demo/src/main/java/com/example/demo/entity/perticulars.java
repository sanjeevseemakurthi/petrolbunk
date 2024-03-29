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
public class perticulars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountid;
    private LocalDate date;
    private Long jama;
    private Long karchu;

    @Override
    public String toString() {
        return "perticulars{" +
                "id=" + id +
                ", accountid=" + accountid +
                ", date=" + date +
                ", jama=" + jama +
                ", karchu=" + karchu +
                ", balance=" + balance +
                ", discription='" + discription + '\'' +
                ", cid=" + cid +
                '}';
    }

    private Long balance;
    private String discription;
    private Long cid;

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

    public Long getAccountid() {
        return accountid;
    }

    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getJama() {
        return jama;
    }

    public void setJama(Long jama) {
        this.jama = jama;
    }

    public Long getKarchu() {
        return karchu;
    }

    public void setKarchu(Long karchu) {
        this.karchu = karchu;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

}
