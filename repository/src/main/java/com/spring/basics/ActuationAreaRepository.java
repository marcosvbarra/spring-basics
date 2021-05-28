package com.spring.basics;

import com.spring.basics.entity.ActuationArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActuationAreaRepository extends JpaRepository<ActuationArea, Long> {


    Optional<ActuationArea> findByName(String name);
}
