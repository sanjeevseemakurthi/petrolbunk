package com.example.demo.Repository;

import com.example.demo.entity.calibration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface calibrationRepository extends JpaRepository<calibration,Long> {
    List<calibration> findAll();
}
