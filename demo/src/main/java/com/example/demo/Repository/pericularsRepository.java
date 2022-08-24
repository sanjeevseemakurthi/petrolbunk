package com.example.demo.Repository;

import com.example.demo.entity.perticulars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface pericularsRepository   extends JpaRepository<perticulars,Long> {

    List<perticulars> findByAccountidAndDate(Long accountid, LocalDate date);
    List<perticulars> findByDate(LocalDate date);
    List<perticulars> findByDateGreaterThanAndAccountid(LocalDate date,Long accountid);
}
