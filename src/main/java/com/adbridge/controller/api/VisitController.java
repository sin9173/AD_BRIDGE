package com.adbridge.controller.api;

import com.adbridge.dto.response.ResponseDto;
import com.adbridge.dto.response.SingleResponseDto;
import com.adbridge.dto.response.visit.VisitListResDto;
import com.adbridge.service.visit.VisitService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/visit")
    public ResponseEntity<ResponseDto> visitSave(HttpServletRequest request) {
        return ResponseEntity.ok(visitService.visitSave(request));
    }

    @GetMapping("/admin/visit/counts")
    public ResponseEntity<SingleResponseDto<Page<VisitListResDto>>> visitCountList(Pageable pageable) {
        return ResponseEntity.ok(visitService.visitList(pageable));
    }
}
