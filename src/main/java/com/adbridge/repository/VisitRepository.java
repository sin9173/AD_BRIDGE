package com.adbridge.repository;

import com.adbridge.dto.response.visit.VisitListResDto;
import com.adbridge.entity.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisitRepository extends JpaRepository<Visit, Long> {


    @Query(value =
        "select " +
            " new com.adbridge.dto.response.visit.VisitListResDto(v.visitDate, count(v.ipAddress)) " +
            " from Visit v " +
            " group by v.visitDate " +
            " order by v.visitDate desc"
    )
    Page<VisitListResDto> findVisitCount(Pageable pageable);
}
