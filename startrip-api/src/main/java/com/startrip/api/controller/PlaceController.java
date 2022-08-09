package com.startrip.api.controller;

import com.startrip.api.service.PlaceService;
import com.startrip.core.dto.PlaceDto;
import com.startrip.core.entity.place.Place;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService){
        this.placeService = placeService;
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @PostMapping("/place")
    public @ResponseBody
    ResponseEntity addPlace(@RequestBody PlaceDto dto) {
        UUID id;
        try{
            id = placeService.createPlace(dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @GetMapping("/place/list")
    public @ResponseBody
    ResponseEntity showPlace() {
        return new ResponseEntity(placeService.allPlace(), HttpStatus.OK);
    }

    @GetMapping("/place/list/{categoryId}")
    public @ResponseBody
    ResponseEntity showPlaceByCategory(@PathVariable("categoryId") UUID category_id) {
        List<Place> placeList;
        try{
            placeList = placeService.categoryPlace(category_id);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(placeList, HttpStatus.OK);
    }

    @GetMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity getPlace(@PathVariable("id") UUID id) {
        Place place;
        try{
            place = placeService.getPlace(id);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(place, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @PostMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity updatePlace(@PathVariable("id") UUID id, PlaceDto dto) {
        try{
            placeService.updatePlace(id, dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("수정되었습니다", HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @DeleteMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity deletePlace(@PathVariable("id") UUID id) {
        try{
            placeService.deletePlace(id);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }

}
