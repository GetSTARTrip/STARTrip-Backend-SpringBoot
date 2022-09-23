package com.startrip.codebase.trip.domain.event.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    public Optional<Event> findByEventTitle(String eventTitle);
}
