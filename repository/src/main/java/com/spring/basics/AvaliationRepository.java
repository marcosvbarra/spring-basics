package com.spring.basics;

import com.spring.basics.entity.Avaliation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliationRepository extends JpaRepository<Avaliation, Long> {

    List<Avaliation> findAllByIdWorker(Long workerId);

}
