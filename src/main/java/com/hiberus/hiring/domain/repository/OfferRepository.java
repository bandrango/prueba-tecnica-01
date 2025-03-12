package com.hiberus.hiring.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiberus.hiring.infrastructure.adapters.outbound.persistence.OfferEntity;


/**
 * Spring Data JPA repository for OfferEntity.
 * Manages persistence of OfferEntity objects in PostgreSQL.
 */
@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {
    List<OfferEntity> findByBrandIdAndPartnumber(Integer brandId, String partnumber);
}