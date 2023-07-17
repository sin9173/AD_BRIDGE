package com.adbridge.controller.api;

import com.adbridge.dto.request.member.MemberModifyReqDto;
import com.adbridge.dto.request.member.MemberPasswordModifyReqDto;
import com.adbridge.dto.request.member.RoleModifyReqDto;
import com.adbridge.dto.request.member.SignUpReqDto;
import com.adbridge.dto.response.ResponseDto;
import com.adbridge.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody SignUpReqDto dto) { //회원 가입
        return ResponseEntity.ok(memberService.signUp(dto));
    }

    @PatchMapping("/member/{id}")
    public ResponseEntity<ResponseDto> memberModify(@PathVariable Long id, @RequestBody MemberModifyReqDto dto) { //회원 정보 수정
        return ResponseEntity.ok(memberService.memberModify(dto, id));
    }

    @PatchMapping("/member/{id}/password")
    public ResponseEntity<ResponseDto> memberPasswordModify(@PathVariable Long id, @RequestBody MemberPasswordModifyReqDto dto) { //비밀번호 수정
        return ResponseEntity.ok(memberService.memberPasswordModify(dto, id));
    }

    @DeleteMapping("/member/{id}")
    public ResponseEntity<ResponseDto> memberDelete(@PathVariable Long id) { //회원 탈퇴
        return ResponseEntity.ok(memberService.memberDelete(id));
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<ResponseDto> memberDetail(@PathVariable Long id) { //회원 상세조회
        return null;
    }

    @GetMapping("/admin/members")
    public ResponseEntity<ResponseDto> memberList() { //회원 리스트 조회 (관리자)
        return null;
    }

    @GetMapping("/admin/member/{id}")
    public ResponseEntity<ResponseDto> adminMemberDetail(@PathVariable Long id) { // 회원 상세 조회 (관리자)
        return null;
    }

    @PatchMapping("/admin/member/{id}/role")
    public ResponseEntity<ResponseDto> memberRoleModify(@PathVariable Long id, @RequestBody RoleModifyReqDto dto) { //회원 권한 수정 (관리자)
        return ResponseEntity.ok(memberService.roleModify(dto, id));
    }
}
