package com.example.demo.Repository;

import com.example.demo.entity.calibration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface calibrationRepository extends JpaRepository<calibration,Long> {
    List<calibration> findAll();

    List<calibration> findAllByCid(Long cid);
    List<calibration> findByCidAndDateGreaterThanEqualAndNextdateLessThanEqual(Long cid, LocalDate date , LocalDate nextdate);
}
