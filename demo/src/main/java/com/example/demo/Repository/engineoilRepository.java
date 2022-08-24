package com.example.demo.Repository;
import com.example.demo.entity.engineoils;
import com.example.demo.entity.readings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface engineoilRepository extends JpaRepository<engineoils,Long>{
    List<engineoils> findAllByDate(LocalDate date);
}
