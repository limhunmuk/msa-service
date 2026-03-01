package com.rodait.pointservice.controller;

import com.rodait.pointservice.dto.AddPointRequestDto;
import com.rodait.pointservice.dto.DeductPointRequestDto;
import com.rodait.pointservice.service.PointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/points")
public class PointInternalController {

    private final PointService pointService;

    public PointInternalController(PointService pointService) {
        this.pointService = pointService;
    }

    @PostMapping("add")
    public ResponseEntity<Void> add(@RequestBody AddPointRequestDto requestDto) {

        pointService.addPoints(requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("deduct")
    public ResponseEntity<Void> deduct(@RequestBody DeductPointRequestDto requestDto) {

        pointService.deductPoints(requestDto);
        return ResponseEntity.noContent().build();
    }
}
