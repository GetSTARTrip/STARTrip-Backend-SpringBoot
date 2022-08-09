package com.startrip.core.entity.favorite_event;

import com.startrip.core.entity.event.Event;
import com.startrip.core.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoriteEventRepository extends JpaRepository<FavoriteEvent, UUID> {
    List<FavoriteEvent> findAllByUserId (User user);
    FavoriteEvent findByEventIdAndUserId (Event event, User user);
}
