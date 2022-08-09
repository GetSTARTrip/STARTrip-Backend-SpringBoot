package com.startrip.core.entity.datatest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataTestRepository extends JpaRepository<DataTest, UUID> {
}
