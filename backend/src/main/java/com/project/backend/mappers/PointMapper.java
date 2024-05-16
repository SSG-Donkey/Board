package com.project.backend.mappers;

import com.project.backend.dto.PointDto;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PointMapper {

 PointDto showPoint(@Param("userNo") String userNo);


}


