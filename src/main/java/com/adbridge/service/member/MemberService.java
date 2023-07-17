package com.adbridge.service.member;

import com.adbridge.dto.request.member.MemberModifyReqDto;
import com.adbridge.dto.request.member.MemberPasswordModifyReqDto;
import com.adbridge.dto.request.member.RoleModifyReqDto;
import com.adbridge.dto.request.member.SignUpReqDto;
import com.adbridge.dto.response.ResponseDto;
import com.adbridge.entity.Member;
import com.adbridge.enums.ResponseResult;
import com.adbridge.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService { //회원 관련 기능

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

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
    public ResponseDto memberModify(MemberModifyReqDto dto, Long memberId) {
        memberRepository.findById(memberId)
                .ifPresent(member -> member.updateInfo(dto));
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    /** 비밀번호 변경 */
    @Transactional
    public ResponseDto memberPasswordModify(MemberPasswordModifyReqDto dto, Long memberId) {
        memberRepository.findById(memberId)
                .ifPresent(member -> member.updatePassword(passwordEncoder.encode(dto.getPassword())));
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    /** 회원 탈퇴 */
    @Transactional
    public ResponseDto memberDelete(Long memberId) {
        memberRepository.findById(memberId)
                .ifPresent(member -> member.delete());
        return new ResponseDto(ResponseResult.SUCCESS);
    }

}
