package com.example.demo.Repository;

import com.example.demo.Response.readingsresponse;
import com.example.demo.entity.readings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.time.LocalDate;

public interface readingsRepository extends JpaRepository<readings,Long> {
    List<readings> findAllByDate(LocalDate date);

    List<readings> findAllByDateAndCid(LocalDate date, Long cid);
}