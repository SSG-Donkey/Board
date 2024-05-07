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
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/region")
@Log
public class RegionController {
    
    @Autowired
    private RegionService regionService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/findAll")
    public ResponseEntity<List<RegionDto>> findAllRegions() {
        List<RegionDto> regions = regionService.findAll();

        for (RegionDto el : regions)
        {
            log.info(el.toString());
        }

        return new ResponseEntity<>(regions, HttpStatus.OK);
    }
    

    @GetMapping("/requestBanks")
    public ResponseEntity<String> requestBanks() {
        // module2의 엔드포인트 URL
        String userUrl = "http://user-service.default.svc.cluster.local:8080/user/banks";

        // module2에 HTTP GET 요청 보내기
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(userUrl, Map.class);

        // 응답에서 은행 목록 가져오기
        Map<String, Object> banksMap = responseEntity.getBody();

        // 여기서 순회하기
        for (String key : banksMap.keySet()) {
            Object value = banksMap.get(key);
            log.info("Key: " + key + ", Value: " + value);
        }


        return new ResponseEntity<>("Banks processed successfully.", HttpStatus.OK);
    }
}
