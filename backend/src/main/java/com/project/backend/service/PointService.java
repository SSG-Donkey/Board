package com.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.backend.dto.PointDto;
import com.project.backend.mappers.PointMapper;
@Service
public class PointService {

    @Autowired
    PointMapper pointMapper;

    public PointDto getUserPoint(String userNo) {
        PointDto res = pointMapper.showPoint(userNo);
        
        if(null == res.getAmount())
            res.setAmount(0);
        
        return res;
    }
}
