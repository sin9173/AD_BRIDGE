package com.adbridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Scope { //제작 범위
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name; //제작 범위명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match; //매칭데이터


    public Scope(String name) {
        this.name = name;
    }


    public void updateMatch(Match match) {
        this.match = match;
    }
}
