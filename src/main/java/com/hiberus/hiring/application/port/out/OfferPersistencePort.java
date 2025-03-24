package com.hiberus.hiring.application.port.out;

import com.hiberus.hiring.domain.model.Offer;

import java.util.List;

/**
 * Outbound port for persistence-related operations on Offers.
 * The implementation lives in the infrastructure layer.
 */
public interface OfferPersistencePort {
	/**
	 * Persists a new offer in the system.
	 *
	 * @param offer the Offer entity to be saved
	 * @return the saved Offer instance, including any auto-generated fields
	 */
	Offer saveOffer(Offer offer);

	/**
	 * Deletes all existing offers from the system.
	 * Use with caution, as this operation is irreversible.
	 */
	void deleteAllOffers();

	/**
	 * Deletes a specific offer identified by its ID.
	 *
	 * @param offerId the unique identifier of the offer to delete
	 */
	void deleteOfferById(Long offerId);

	/**
	 * Retrieves all offers available in the system.
	 *
	 * @return a list of all Offer entities
	 */
	List<Offer> findAllOffers();

	/**
	 * Retrieves a specific offer by its ID.
	 *
	 * @param offerId the unique identifier of the offer
	 * @return the corresponding Offer entity, or null if not found
	 */
	Offer findOfferById(Long offerId);

	/**
	 * Retrieves all offers that match the given brand ID and part number.
	 *
	 * @param brandId the identifier of the brand
	 * @param partNumber the part number to filter by
	 * @return a list of matching Offer entities
	 */
	List<Offer> findOffersByBrandAndPartNumber(Integer brandId, String partNumber);
}