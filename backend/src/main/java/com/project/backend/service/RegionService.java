package com.project.backend.service;

import com.project.backend.dto.RegionDto;
import com.project.backend.mappers.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    @Autowired
    RegionMapper regionMapper;

    public List<RegionDto> findAll(){
        List<RegionDto> res = regionMapper.findAll();

        return res;
    }
}
