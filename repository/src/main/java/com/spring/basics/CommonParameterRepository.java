package com.spring.basics;

import com.spring.basics.entity.CommonParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonParameterRepository extends JpaRepository<CommonParameter, Long> {

    CommonParameter findByName(String name);
}
