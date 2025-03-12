package com.hiberus.hiring.application.port.in;

import com.hiberus.hiring.domain.model.Offer;

import java.util.List;

/**
 * Inbound port (Use Case interface) defining operations
 * for managing Offers in the application.
 */
public interface OfferUseCase {
    Offer createOffer(Offer offer);
    void deleteAllOffers();
    void deleteOfferById(Long offerId);
    List<Offer> getAllOffers();
    Offer getOfferById(Long offerId);

    /**
     * Retrieves a 'flattened' list of offers for a specific brand and partnumber.
     * The result should have no overlapping intervals.
     */
    List<Offer> getTimetableForBrandAndPartNumber(Integer brandId, String partNumber);
}