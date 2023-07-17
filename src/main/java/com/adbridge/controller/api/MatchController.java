package com.adbridge.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MatchController {


    @PostMapping("/match")
    public String matchSave() { //매칭데이터 등록
        return null;
    }

    @PatchMapping("/match/{id}")
    public String matchModify(@PathVariable Long id, HttpServletRequest request) { //매칭데이터 수정
        return null;
    }

    @DeleteMapping("/match/{id}")
    public String matchDelete(@PathVariable Long id, HttpServletRequest request) { //매칭데이터 삭제
        return null;
    }

    @GetMapping("/matches")
    public String matchSearch() { //매칭데이터 리스트 조회(검색)
        return null;
    }

    @GetMapping("/match/{id}")
    public String matchDetail(@PathVariable Long id) { //매칭데이터 상세 조회
        return null;
    }
}
