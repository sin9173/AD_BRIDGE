package com.adbridge.controller.api;

import com.adbridge.dto.request.match.MatchModifyReqDto;
import com.adbridge.dto.request.match.MatchSaveReqDto;
import com.adbridge.dto.request.match.MatchSearchReqDto;
import com.adbridge.dto.response.ResponseDto;
import com.adbridge.dto.response.SingleResponseDto;
import com.adbridge.dto.response.match.MatchDetailResDto;
import com.adbridge.dto.response.match.MatchListResDto;
import com.adbridge.exception.InvalidTokenException;
import com.adbridge.service.match.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/match")
    public ResponseEntity<ResponseDto> matchSave(@RequestBody MatchSaveReqDto dto) { //매칭데이터 등록
        return ResponseEntity.ok(matchService.matchSave(dto));
    }

    @PatchMapping("/match/{id}")
    public ResponseEntity<ResponseDto> matchModify(@PathVariable Long id, @RequestBody MatchModifyReqDto dto, HttpServletRequest request) { //매칭데이터 수정
        return ResponseEntity.ok(matchService.matchModify(dto, id, request));
    }

    @DeleteMapping("/match/{id}")
    public ResponseEntity<ResponseDto> matchDelete(@PathVariable Long id, HttpServletRequest request) { //매칭데이터 삭제
        return ResponseEntity.ok(matchService.matchDelete(id, request));
    }

    @GetMapping("/admin/matches")
    public ResponseEntity<SingleResponseDto<Page<MatchListResDto>>> matchSearchAdmin(@RequestBody MatchSearchReqDto dto, Pageable pageable) { //매칭데이터 리스트 조회(검색)
        return ResponseEntity.ok(matchService.matchSearchAdmin(dto, pageable));
    }

    @GetMapping("/matches")
    public ResponseEntity<SingleResponseDto<Page<MatchListResDto>>> matchSearch(@RequestBody MatchSearchReqDto dto, Pageable pageable, HttpServletRequest request) throws InvalidTokenException { //매칭데이터 리스트 조회(검색)
        return ResponseEntity.ok(matchService.matchSearchUser(dto, pageable, request));
    }

    @GetMapping("/admin/match/{id}")
    public ResponseEntity<SingleResponseDto<MatchDetailResDto>> matchDetailAdmin(@PathVariable Long id) { //매칭데이터 상세 조회
        return ResponseEntity.ok(matchService.matchDetailAdmin(id));
    }

    @GetMapping("/match/{id}")
    public ResponseEntity<SingleResponseDto<MatchDetailResDto>> matchDetail(@PathVariable Long id, HttpServletRequest request) { //매칭데이터 상세 조회
        return ResponseEntity.ok(matchService.matchDetailUser(id, request));
    }
}
