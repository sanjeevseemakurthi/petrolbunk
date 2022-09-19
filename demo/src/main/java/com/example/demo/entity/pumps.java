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
public class pumps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private String tank;
    private String product;
    private Double price;
    private Long latestclosedreading;
    private Long latestopenreading;
    private Long cid;

    public Long getCid() {
        return cid;
    }

    @Override
    public String toString() {
        return "pumps{" +
                "id=" + id +
                ", number=" + number +
                ", tank='" + tank + '\'' +
                ", product='" + product + '\'' +
                ", price=" + price +
                ", latestclosedreading=" + latestclosedreading +
                ", latestopenreading=" + latestopenreading +
                ", cid=" + cid +
                '}';
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

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getTank() {
        return tank;
    }

    public void setTank(String tank) {
        this.tank = tank;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getLatestclosedreading() {
        return latestclosedreading;
    }

    public void setLatestclosedreading(Long latestclosedreading) {
        this.latestclosedreading = latestclosedreading;
    }

    public Long getLatestopenreading() {
        return latestopenreading;
    }

    public void setLatestopenreading(Long latestopenreading) {
        this.latestopenreading = latestopenreading;
    }

}
