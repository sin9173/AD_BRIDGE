package com.adbridge.repository;

import com.adbridge.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    List<RefreshToken> findByUsernameAndExpireDateAfter(String username, LocalDateTime date);


    List<RefreshToken> findByUsername(String username);
}
