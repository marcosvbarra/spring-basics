package com.spring.basics;

import com.spring.basics.entity.JobRequest;
import com.spring.basics.entity.JobRequestStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface JobRequestRepository extends JpaRepository<JobRequest, Long> {

    @Transactional
    @Modifying
    @Query("update JobRequest job set job.status = :status where job.id = :id")
    int updateStatus(@Param("status") JobRequestStatusEnum status, @Param("id") Long id);

}
