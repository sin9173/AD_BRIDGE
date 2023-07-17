package com.adbridge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String refreshToken;

    private LocalDateTime expireDate;


    public RefreshToken(String username, String refreshToken, Long timeMillis) {
        this.username = username;
        this.refreshToken = refreshToken;
        this.expireDate = LocalDateTime.now().plusSeconds((timeMillis/1000));
    }
}
