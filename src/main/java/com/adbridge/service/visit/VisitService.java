package com.adbridge.service.visit;

import com.adbridge.dto.response.ResponseDto;
import com.adbridge.dto.response.SingleResponseDto;
import com.adbridge.dto.response.visit.VisitListResDto;
import com.adbridge.entity.Visit;
import com.adbridge.enums.ResponseResult;
import com.adbridge.repository.VisitRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;


    // 방문기록 저장
    public ResponseDto visitSave(HttpServletRequest request) {
        String clientIP = getClientIP(request);
        Visit visit = new Visit(clientIP);
        visitRepository.save(visit);
        return new ResponseDto(ResponseResult.SUCCESS);
    }

    // 일자별 방문기록 조회
    public SingleResponseDto<Page<VisitListResDto>> visitList(Pageable pageable) {
        return new SingleResponseDto("0", "성공", visitRepository.findVisitCount(pageable));
    }

    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARD_For");
        if(ip == null) ip = request.getHeader("Proxy-Client-IP");
        if(ip == null) ip = request.getHeader("WL-Proxy-Client-IP");
        if(ip == null) ip = request.getHeader("HTTP_Client_IP");
        if(ip == null) ip = request.getHeader("HTTP_X_FORWARD_FOR");
        if(ip == null) ip = request.getRemoteAddr();
        return ip;
    }
}
