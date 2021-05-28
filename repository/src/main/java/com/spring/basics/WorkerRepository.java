package com.spring.basics;

import com.spring.basics.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByCpf(String cpf);
    Optional<Worker> findByEmail(String email);

}
