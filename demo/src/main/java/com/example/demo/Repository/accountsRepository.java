package com.example.demo.Repository;
import com.example.demo.entity.account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface accountsRepository  extends JpaRepository<account,Long> {
    @Override
    void deleteAllInBatch(Iterable<account> entities);

    List<account> findByDate(LocalDate date);
    List<account> findByName(String name);
}
