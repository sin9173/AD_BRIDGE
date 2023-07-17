package com.adbridge.service.member;

import com.adbridge.dto.request.member.*;
import com.adbridge.dto.response.ListResponseDto;
import com.adbridge.dto.response.ResponseDto;
import com.adbridge.dto.response.SingleResponseDto;
import com.adbridge.dto.response.member.MemberDetailResDto;
import com.adbridge.dto.response.member.MemberListResDto;
import com.adbridge.entity.Member;
import com.adbridge.enums.ResponseResult;
import com.adbridge.repository.MemberRepository;
import com.adbridge.repository.MemberRepositoryQuery;
import com.adbridge.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService { //회원 관련 기능

    private final MemberRepository memberRepository;

    private final MemberRepositoryQuery memberRepositoryQuery;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    /** 회원 가입 */
    public ResponseDto signUp(SignUpReqDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Member member = new Member(dto);
        memberRepository.save(member);
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    /** 권한 변경 */
    @Transactional
    public ResponseDto roleModify(RoleModifyReqDto dto, Long memberId) {
        memberRepository.findById(memberId)
                .ifPresent(member -> member.updateRole(dto.getRole()));
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    /** 회원 정보 변경 */
    @Transactional
    public ResponseDto memberModify(MemberModifyReqDto dto, Long memberId, HttpServletRequest request) {
        jwtUtils.personalCheck(memberId, request);
        memberRepository.findById(memberId)
                .ifPresent(member -> member.updateInfo(dto));
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    /** 비밀번호 변경 */
    @Transactional
    public ResponseDto memberPasswordModify(MemberPasswordModifyReqDto dto, Long memberId, HttpServletRequest request) {
        jwtUtils.personalCheck(memberId, request);
        memberRepository.findById(memberId)
                .ifPresent(member -> member.updatePassword(passwordEncoder.encode(dto.getPassword())));
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    /** 회원 탈퇴 */
    @Transactional
    public ResponseDto memberDelete(Long memberId, HttpServletRequest request) {
        jwtUtils.personalCheck(memberId, request);
        memberRepository.findById(memberId)
                .ifPresent(member -> member.delete());
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    /** 회원 상세 조회 */
    public SingleResponseDto<MemberDetailResDto> memberDetail(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        return new SingleResponseDto("0", "성공", new MemberDetailResDto(member));
    }

    /** 회원 리스트 조회 */
    public SingleResponseDto<Page<MemberListResDto>> memberSearch(MemberSearchReqDto dto, Pageable pageable) {
        return new SingleResponseDto("0", "성공", memberRepositoryQuery.memberSearch(dto, pageable));
    }



}
