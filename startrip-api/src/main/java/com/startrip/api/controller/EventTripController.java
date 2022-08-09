package com.startrip.api.controller;

import com.startrip.api.service.trip.EventTripService;
import com.startrip.core.entity.event_trip.EventTrip;
import com.startrip.core.entity.event_trip.dto.CreateEventTripDto;
import com.startrip.core.entity.event_trip.dto.ResponseEventTripDto;
import com.startrip.core.entity.event_trip.dto.UpdateEventTripDto;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EventTripController {
    private final EventTripService eventTripService;

    @Autowired
    public EventTripController(EventTripService eventTripService) {
        this.eventTripService = eventTripService;
    }

    // Create
    @PostMapping("/eventtrip")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity createEventTrip(@RequestBody CreateEventTripDto dto) {
        UUID id = eventTripService.createEventTrip(dto);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    // All
    @GetMapping("/eventtrip")
    public List<ResponseEventTripDto> getAllEventTrip() {
        List<ResponseEventTripDto> eventTrip = eventTripService.allEventTrip();
        return eventTrip;
    }

    // Get
    @GetMapping("/eventtrip/{id}")
    public ResponseEntity getEventTrip(@PathVariable("id") UUID id) {
        EventTrip eventTrip;
        try {
            eventTrip = eventTripService.getEventTrip(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(eventTrip, HttpStatus.OK);
    }

    // Update
    @PostMapping("/eventtrip/{id}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity updateEventTrip(@PathVariable("id") UUID id, @RequestBody UpdateEventTripDto dto) {
        try{
            eventTripService.updateEventTrip(id, dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("", HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/eventtrip/{id}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity deleteEventTrip(@PathVariable("id") UUID id){
        try {
            eventTripService.deleteEventTrip(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Event Trip 삭제", HttpStatus.OK);
    }
}
