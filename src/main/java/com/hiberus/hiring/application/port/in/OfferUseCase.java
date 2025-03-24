package com.hiberus.hiring.application.port.in;

import com.hiberus.hiring.domain.model.Offer;

import java.util.List;

/**
 * Inbound port (Use Case interface) defining operations
 * for managing Offers in the application.
 */
public interface OfferUseCase {
	/**
	 * Creates and persists a new offer in the system.
	 *
	 * @param offer the Offer entity to be created
	 * @return the created Offer entity, including any generated fields
	 */
	Offer createOffer(Offer offer);

	/**
	 * Deletes all offers from the system.
	 * <p>
	 * This operation is irreversible and should be used with caution.
	 */
	void deleteAllOffers();

	/**
	 * Deletes a specific offer identified by its unique ID.
	 *
	 * @param offerId the ID of the offer to be deleted
	 */
	void deleteOfferById(Long offerId);

	/**
	 * Retrieves all offers available in the system.
	 *
	 * @return a list of all Offer entities
	 */
	List<Offer> getAllOffers();

	/**
	 * Retrieves a specific offer by its unique ID.
	 *
	 * @param offerId the ID of the offer to retrieve
	 * @return the matching Offer entity, or {@code null} if not found
	 */
	Offer getOfferById(Long offerId);

	/**
	 * Retrieves a list of offers for the given brand and part number, with no overlapping intervals.
	 * <p>
	 * The returned list is "flattened" to ensure each offer has a unique, non-conflicting time period.
	 *
	 * @param brandId the ID of the brand
	 * @param partNumber the part number to search for
	 * @return a list of non-overlapping Offer entities for the specified brand and part number
	 */
	List<Offer> getTimetableForBrandAndPartNumber(Integer brandId, String partNumber);
}