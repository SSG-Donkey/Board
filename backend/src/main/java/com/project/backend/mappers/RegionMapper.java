package com.project.backend.mappers;

import com.project.backend.dto.RegionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegionMapper {
    List<RegionDto> findAll();
}
