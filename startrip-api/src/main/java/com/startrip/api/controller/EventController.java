package com.startrip.api.controller;

import com.startrip.api.service.EventService;
import com.startrip.core.entity.event.Event;
import com.startrip.core.entity.event.dto.CreateEventDto;
import com.startrip.core.entity.event.dto.ResponseEventDto;
import com.startrip.core.entity.event.dto.UpdateEventDto;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //전체 조회
    @GetMapping("/event")
    public List<ResponseEventDto> getEvents() {
        List<ResponseEventDto> events = eventService.allEvent();
        return events;
        //ToDo: try-catch로 예외처리하기
    }

    //생성
    @PostMapping("/event")
    public ResponseEntity
    createEvent(@RequestBody CreateEventDto dto) {
        eventService.createEvent(dto);
        return new ResponseEntity("이벤트 생성", HttpStatus.OK);
    }

    //상세 조회
    @GetMapping(value ={"/event/{id}"})
    public ResponseEntity getEvent(@PathVariable("id") UUID id) {
        Event event;
        try{
            event = eventService.getEvent(id);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(event, HttpStatus.OK);
    }

    //수정
    @PutMapping("/event/{id}")
    public ResponseEntity updateEvent(@PathVariable("id") UUID id, @RequestBody UpdateEventDto dto) {
        try{
            eventService.updateEvent(id, dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("", HttpStatus.OK);
    }

    //삭제
    @DeleteMapping("/event/{id}")
    public String deleteEvent(@PathVariable("id") UUID id){
        eventService.deleteEvent(id);
        return "삭제";
    }


}