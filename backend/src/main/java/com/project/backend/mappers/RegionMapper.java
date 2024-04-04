package com.project.backend.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.backend.dto.RegionDto;

@Mapper
public interface RegionMapper {
    List<RegionDto> findAll();
}
