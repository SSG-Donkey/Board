package com.project.backend.service;

import com.project.backend.dto.PointDto;
import com.project.backend.mappers.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PointService {

    @Autowired
    PointMapper pointMapper;

    public Integer showPoint(Integer userNo){
        int res = pointMapper.showPoint(userNo);

        return res;
    }
}