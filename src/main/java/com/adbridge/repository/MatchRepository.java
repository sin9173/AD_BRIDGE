package com.adbridge.repository;

import com.adbridge.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Matching, Long> {

}
