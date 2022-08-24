package com.example.demo.entity;

import org.springframework.stereotype.Controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Controller
@Entity
public class engineoilsstock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long qtyleft;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQtyleft() {
        return qtyleft;
    }

    public void setQtyleft(Long qtyleft) {
        this.qtyleft = qtyleft;
    }

    @Override
    public String toString() {
        return "engineoilsstock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qtyleft=" + qtyleft +
                '}';
    }
}
