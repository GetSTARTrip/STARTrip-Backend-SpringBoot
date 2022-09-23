package com.startrip.codebase.curation.repository;

import com.startrip.codebase.curation.domain.CurationObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurationObjectRepository extends JpaRepository<CurationObject, UUID> {

}
