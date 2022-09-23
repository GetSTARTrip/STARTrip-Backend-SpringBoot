package com.startrip.codebase.trip.domain.event.domain.favorite_event;

import com.startrip.codebase.trip.domain.event.domain.event.Event;
import com.startrip.codebase.member.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoriteEventRepository extends JpaRepository<FavoriteEvent, UUID> {
    List<FavoriteEvent> findAllByUserId (User user);
    FavoriteEvent findByEventIdAndUserId (Event event, User user);
}
