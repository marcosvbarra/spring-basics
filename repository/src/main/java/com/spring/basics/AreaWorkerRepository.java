package com.spring.basics;

import com.spring.basics.entity.AreaWorker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AreaWorkerRepository extends JpaRepository<AreaWorker, Long> {

    List<AreaWorker> findAllByIdWorker(Long idWorker);


}
