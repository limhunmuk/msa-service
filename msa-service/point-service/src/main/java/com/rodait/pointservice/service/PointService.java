package com.rodait.pointservice.service;

import com.rodait.pointservice.domain.Point;
import com.rodait.pointservice.domain.PointRepository;
import com.rodait.pointservice.dto.AddPointRequestDto;
import com.rodait.pointservice.dto.DeductPointRequestDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Transactional
    public void addPoints(AddPointRequestDto requestDto) {
        Point point = pointRepository.findByUserId(requestDto.getUserId())
                .orElseGet((()-> new Point(requestDto.getUserId(), 0)));

        point.addAmount(requestDto.getAmount());
        pointRepository.save(point);
    }

    @Transactional
    public void deductPoints(DeductPointRequestDto requestDto) {

        Point point = pointRepository.findByUserId(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("포인트가 존재하지 않습니다."));

        point.deductAmount(requestDto.getAmount());
        pointRepository.save(point);
    }
}
