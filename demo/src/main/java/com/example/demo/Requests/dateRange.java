package com.example.demo.Requests;

import java.time.LocalDate;

public class dateRange {
    public LocalDate initialdate;
    public LocalDate finaldate;

    public LocalDate getInitialdate() {
        return initialdate;
    }

    public void setInitialdate(LocalDate initialdate) {
        this.initialdate = initialdate;
    }

    public LocalDate getFinaldate() {
        return finaldate;
    }

    public void setFinaldate(LocalDate finaldate) {
        this.finaldate = finaldate;
    }
}
