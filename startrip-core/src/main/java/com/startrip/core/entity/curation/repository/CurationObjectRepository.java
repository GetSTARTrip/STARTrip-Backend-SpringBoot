package com.startrip.core.entity.curation.repository;

import com.startrip.core.entity.curation.entity.CurationObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurationObjectRepository extends JpaRepository<CurationObject, UUID> {

}
