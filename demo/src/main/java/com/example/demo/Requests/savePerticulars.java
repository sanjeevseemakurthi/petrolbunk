package com.example.demo.Requests;
import com.example.demo.entity.perticulars;

import java.time.LocalDate;
import java.util.List;

public class savePerticulars {
    public LocalDate date;
    public List <perticulars> data;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<perticulars> getData() {
        return data;
    }

    public void setData(List<perticulars> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "savePerticulars{" +
                "date=" + date +
                ", data=" + data +
                '}';
    }
}
