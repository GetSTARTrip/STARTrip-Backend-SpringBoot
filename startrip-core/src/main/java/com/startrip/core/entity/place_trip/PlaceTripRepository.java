package com.startrip.core.entity.place_trip;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PlaceTripRepository extends JpaRepository<PlaceTrip, UUID> {
}
