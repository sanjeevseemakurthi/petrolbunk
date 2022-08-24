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
    private String Product;
    private Long Price;
    private Long latestclosedreading;
    private Long latestopenreading;
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
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
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

    @Override
    public String toString() {
        return "pumps{" +
                "id=" + id +
                ", number=" + number +
                ", tank='" + tank + '\'' +
                ", Product='" + Product + '\'' +
                ", Price=" + Price +
                ", latestclosedreading=" + latestclosedreading +
                ", latestopenreading=" + latestopenreading +
                '}';
    }
}
