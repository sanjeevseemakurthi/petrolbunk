package com.example.demo.Repository;
import com.example.demo.entity.account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface accountsRepository  extends JpaRepository<account,Long> {
    @Override
    void deleteAllInBatch(Iterable<account> entities);

    List<account> findByDate(LocalDate date);
    List<account> findByName(String name);

    List<account> findByCid(Long cid);

    List<account> findByNameAndCid(String cash, Long cid);

    Optional<account> findByIdAndCid(Long accountid, Long cid);

    List<account> findAllByCid(Long cid);

    List<account> findAllByCidAndOnlyadmin(Long cid, boolean onlyadmin);
}
