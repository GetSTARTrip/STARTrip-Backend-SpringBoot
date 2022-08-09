package com.startrip.api.service;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.event_review.EventReview;
import com.startrip.codebase.domain.event_review.EventReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class EventServiceTest {
    private final UUID eventReviewId = UUID.randomUUID();

    @Autowired
    private EventService eventService;

    @Autowired
    private EventReviewService eventReviewService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventReviewRepository eventReviewRepository;

    @BeforeEach
    public void setup() {
    }

    private void createEventReview(Event event){
        EventReview eventReview = EventReview.builder()
                .event(event)
                .reviewId(eventReviewId)
                .eventReviewTitle("이벤트리뷰제목")
                .text("이벤트리뷰내용")
                .reviewRate(3.5)
                .build();

        eventReviewRepository.save(eventReview);
    }
/*
    private void createEvent() {
        EventReview eventReview = EventReview.builder()
                .reviewId(eventReviewId)
                .eventReviewTitle("이벤트리뷰제목?")
                .text("이벤트리뷰내용?")
                .reviewRate(3.5)
                .build();
        eventReviewRepository.save(eventReview);
    }

 */

    @DisplayName("이벤트리뷰 등록 시 이벤트와 매핑된다.")
    @Test
    void eventReview_event(){
        Event event = Event.builder()
                .eventId(eventReviewId)
                .eventTitle("서울 롯데월드")
                .description("환상의 나라 에버랜드로~")
                .contact("02-123-421")
                .build();

        eventRepository.save(event);

        createEventReview(event);

        List<EventReview> eventReviews = eventReviewRepository.findByEventId(event.getEventId());
        EventReview reviews = eventReviews.get(0);
        Event find = reviews.getEvent();

        Assertions.assertThat(find.getEventId()).isEqualTo(event.getEventId());
    }
}