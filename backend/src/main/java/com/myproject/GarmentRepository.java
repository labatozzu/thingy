package com.myproject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GarmentRepository extends JpaRepository<Garment, Long> {

    List<Garment> findByOwner_IdOrderByIdDesc(Long ownerId);

    Optional<Garment> findByIdAndOwner_Id(Long id, Long ownerId);
}
