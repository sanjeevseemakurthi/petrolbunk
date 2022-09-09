package com.example.demo.Repository;
import com.example.demo.entity.pumps;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface pumpsRepository extends JpaRepository<pumps,Long> {
    List<pumps> findAllByCid(Long cid);

}
