package com.project.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.backend.dto.RegionDto;
import com.project.backend.mappers.RegionMapper;

@Service
public class RegionService {
    @Autowired
    RegionMapper regionMapper;

    public List<RegionDto> findAll(){
        List<RegionDto> res = regionMapper.findAll();

        return res;
    }
}
