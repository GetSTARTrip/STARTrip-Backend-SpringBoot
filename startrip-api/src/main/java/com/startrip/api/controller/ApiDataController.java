package com.startrip.api.controller;

import com.startrip.core.util.apidata.BigDataApi;
import com.startrip.core.util.apidata.KorInfoApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/place")
public class ApiDataController {
    private final BigDataApi bigDataApi;
    private final KorInfoApi korInfoApi;

    public ApiDataController(BigDataApi bigDataApi, KorInfoApi korInfoApi) {
        this.bigDataApi = bigDataApi;
        this.korInfoApi = korInfoApi;
    }

    @GetMapping("/bigdata")
    public @ResponseBody
    ResponseEntity getPlace() {
        try {
            String date = bigDataApi.getApiResult("202106");
            return new ResponseEntity(date, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/korinfo")
    public @ResponseBody
    ResponseEntity getKorInfo() {
        try {
            return new ResponseEntity(korInfoApi.getApiResult(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
