package com.expanse.expanse.manager.repository;

import com.expanse.expanse.manager.entity.ExMg;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExMgRepo extends CrudRepository<ExMg, Long> {
//    List<ExMg> findByCategory(String category);

    List<ExMg> findByDateAndEmailId(LocalDate date, String emailId);

    List<ExMg> findAllByEmailId(String userId);

    List<ExMg> findByTypeAndEmailId(String type, String emailId);

//    @Query("SELECT sum(ex.amount) FROM ExMg ex")
//    double getTotalAmount();
}
