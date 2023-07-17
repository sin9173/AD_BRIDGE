package com.adbridge.repository;

import com.adbridge.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsernameAndDeleteYnFalse(String username);

}
