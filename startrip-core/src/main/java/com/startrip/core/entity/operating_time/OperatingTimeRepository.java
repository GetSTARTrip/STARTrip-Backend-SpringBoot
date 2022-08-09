package com.startrip.core.entity.operating_time;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OperatingTimeRepository extends JpaRepository<OperatingTime, UUID> {

    List<OperatingTime> findAllByPlaceId(UUID placeId);
}
