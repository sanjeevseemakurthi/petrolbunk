package com.example.demo.Repository;
import com.example.demo.entity.engineoilsstock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface engineoilstocksRepository extends JpaRepository<engineoilsstock,Long> {
    List<engineoilsstock> findAllByCid(Long cid);
}
