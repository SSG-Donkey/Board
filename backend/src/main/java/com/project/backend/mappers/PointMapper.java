package com.project.backend.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.backend.dto.PointDto;

@Mapper
public interface PointMapper {

 PointDto showPoint(@Param("userNo") String userNo);


}


