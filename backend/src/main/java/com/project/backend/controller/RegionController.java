package com.project.backend.controller;

import com.project.backend.dto.RegionDto;
import com.project.backend.service.RegionService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/region")
@Log
public class RegionController {
    
    @Autowired
    private RegionService regionService;

    @GetMapping("/findAll")
    public ResponseEntity<List<RegionDto>> findAllRegions() {
        List<RegionDto> regions = regionService.findAll();

        for (RegionDto el : regions)
        {
            log.info(el.toString());
        }

        return new ResponseEntity<>(regions, HttpStatus.OK);
    }
    
}
