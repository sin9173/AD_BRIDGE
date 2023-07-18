package com.adbridge.service.match;

import com.adbridge.dto.request.match.MatchModifyReqDto;
import com.adbridge.dto.request.match.MatchSaveReqDto;
import com.adbridge.dto.request.match.MatchSearchReqDto;
import com.adbridge.dto.response.ResponseDto;
import com.adbridge.dto.response.SingleResponseDto;
import com.adbridge.dto.response.match.MatchDetailResDto;
import com.adbridge.dto.response.match.MatchListResDto;
import com.adbridge.entity.Match;
import com.adbridge.entity.Member;
import com.adbridge.entity.Scope;
import com.adbridge.enums.ResponseResult;
import com.adbridge.exception.InvalidTokenException;
import com.adbridge.repository.MatchRepository;
import com.adbridge.repository.MatchRepositoryQuery;
import com.adbridge.repository.MemberRepository;
import com.adbridge.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private final MatchRepositoryQuery matchRepositoryQuery;

    private final MemberRepository memberRepository;

    private final JwtUtils jwtUtils;

    /** 매칭데이터 등록 */
    @Transactional
    public ResponseDto matchSave(MatchSaveReqDto dto) {
        Member member = memberRepository.findById(dto.getMember_id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Match match = new Match(dto, member);
        dto.getScope_list()
                .forEach((scope) -> match.addScope(new Scope(scope.getName())));
        matchRepository.save(match);
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    /** 매칭데이터 수정 */
    public ResponseDto matchModify(MatchModifyReqDto dto, Long id, HttpServletRequest request) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매칭데이터입니다."));
        jwtUtils.personalCheck(match.getMember().getId(), request);
        match.updateInfo(dto);
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    /** 매칭데이터 삭제 */
    public ResponseDto matchDelete(Long id, HttpServletRequest request) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매칭데이터입니다."));
        jwtUtils.personalCheck(match.getMember().getId(), request);
        match.delete();
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    /** 매칭데이터 리스트 조회 (검색) */
    public SingleResponseDto<Page<MatchListResDto>> matchSearchAdmin(MatchSearchReqDto dto, Pageable pageable) {
        Page<MatchListResDto> matchList = matchRepositoryQuery.matchSearch(dto, pageable);
        return new SingleResponseDto("0", "성공", matchList);
    }

    public SingleResponseDto<Page<MatchListResDto>> matchSearchUser(MatchSearchReqDto dto, Pageable pageable, HttpServletRequest request) throws InvalidTokenException {
        Long memberId = jwtUtils.getMemberId(request);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidTokenException("토큰의 회원 정보가 잘못되었습니다."));
        dto.setUsername(member.getUsername());
        Page<MatchListResDto> matchList = matchRepositoryQuery.matchSearch(dto, pageable);
        return new SingleResponseDto("0", "성공", matchList);
    }

    /** 매칭데이터 상세 조회 */
    public SingleResponseDto<MatchDetailResDto> matchDetailAdmin(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매칭데이터입니다."));
        return new SingleResponseDto("0", "성공", new MatchDetailResDto(match));
    }

    public SingleResponseDto<MatchDetailResDto> matchDetailUser(Long id, HttpServletRequest request) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매칭데이터입니다."));
        Long memberId = jwtUtils.getMemberId(request);
        if(match.getMember().getId()!=memberId) throw new BadCredentialsException("본인의 매칭데이터가 아닙니다.");
        return new SingleResponseDto("0", "성공", new MatchDetailResDto(match));
    }
}
