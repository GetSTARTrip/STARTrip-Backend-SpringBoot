package com.startrip.api.service;

import com.startrip.core.entity.event.Event;
import com.startrip.core.entity.event.EventRepository;
import com.startrip.core.entity.event.dto.CreateEventDto;
import com.startrip.core.entity.event.dto.ResponseEventDto;
import com.startrip.core.entity.event.dto.UpdateEventDto;
import com.startrip.core.entity.event_review.EventReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final EventReviewRepository eventReviewRepository;

    @Autowired
    public EventService(EventRepository eventRepository, EventReviewRepository eventReviewRepository) {
        this.eventRepository = eventRepository;
        this.eventReviewRepository = eventReviewRepository;
    }

    public void createEvent(CreateEventDto dto) {
        Event event = Event.builder()
                .eventId(dto.getEventId())
                .eventTitle(dto.getEventTitle())
                .description(dto.getDescription())
                .contact(dto.getContact())
                .build();
        eventRepository.save(event);
        log.info(event.toString());
    }

    public List<ResponseEventDto> allEvent() {
        List<Event> events = eventRepository.findAll();
        List<ResponseEventDto> dtos = new ArrayList<>();

        for (Event event : events) {
            ResponseEventDto dto = new ResponseEventDto();
            dto.setEventTitle(event.getEventTitle());
            dto.setStartDate(event.getStartDate());
            dto.setEndDate(event.getEndDate());

            dtos.add(dto);
        }
        return dtos;
    }

    public Event getEvent(UUID id){
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 이벤트가 없습니다."));
        return event;
    }

    public void updateEvent(UUID id, UpdateEventDto dto){
        // 조회
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()){
            throw new RuntimeException("존재하지 않는 이벤트입니다.");
        }
        Event use = event.get();
        
        use.update(dto);
        
        eventRepository.save(use);
    }

    public void deleteEvent( UUID id){
        eventRepository.deleteById(id);
    }
}
