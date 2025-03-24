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
	/**
	 * Retrieves all offers that match the specified brand ID and part number.
	 *
	 * @param brandId the ID of the brand to filter by
	 * @param partnumber the part number to filter by
	 * @return a list of OfferEntity instances that match the given brand ID and part number
	 */
	List<OfferEntity> findByBrandIdAndPartnumber(Integer brandId, String partnumber);
}