package com.startrip.api.controller;

import com.startrip.api.service.PlaceInfoService;
import com.startrip.core.dto.PlaceInfoDto;
import com.startrip.core.entity.place_info.PlaceInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PlaceInfoController {

    private PlaceInfoService placeInfoService;

    public PlaceInfoController(PlaceInfoService placeInfoService) {
        this.placeInfoService = placeInfoService;
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @PostMapping("/place/info")
    public @ResponseBody
    ResponseEntity addPlaceInfo(@RequestBody PlaceInfoDto dto) {
        Long id;
        try{
            id = placeInfoService.createPlaceInfo(dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @GetMapping("/place/info/{id}")
    public @ResponseBody
    ResponseEntity getPlaceInfo(@PathVariable("id") Long id) {
        PlaceInfo placeInfo;
        try{
            placeInfo = placeInfoService.getPlaceInfo(id);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(placeInfo, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @PostMapping("/place/info/{id}")
    public @ResponseBody
    ResponseEntity updatePlaceInfo(@PathVariable("id") Long id, PlaceInfoDto dto) {
        try{
            placeInfoService.updatePlaceInfo(id, dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("수정되었습니다", HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @DeleteMapping("/place/info/{id}")
    public @ResponseBody
    ResponseEntity deletePlaceInfo(@PathVariable("id") Long id) {
        try{
            placeInfoService.deletePlaceInfo(id);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }
}
