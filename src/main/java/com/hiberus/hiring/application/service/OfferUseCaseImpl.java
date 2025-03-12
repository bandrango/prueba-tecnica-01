package com.hiberus.hiring.application.service;

import com.hiberus.hiring.application.port.in.OfferUseCase;
import com.hiberus.hiring.application.port.out.OfferPersistencePort;
import com.hiberus.hiring.domain.model.Offer;
import com.hiberus.hiring.domain.service.OfferDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements the OfferUseCase interface.
 * Orchestrates domain logic via OfferDomainService
 * and relies on OfferPersistencePort for persistence.
 */
@Service
@RequiredArgsConstructor
public class OfferUseCaseImpl implements OfferUseCase {

    private final OfferPersistencePort offerPersistencePort;
    private final OfferDomainService offerDomainService;

    @Override
    public Offer createOffer(Offer offer) {
        return offerPersistencePort.saveOffer(offer);
    }

    @Override
    public void deleteAllOffers() {
        offerPersistencePort.deleteAllOffers();
    }

    @Override
    public void deleteOfferById(Long offerId) {
        offerPersistencePort.deleteOfferById(offerId);
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerPersistencePort.findAllOffers();
    }

    @Override
    public Offer getOfferById(Long offerId) {
        return offerPersistencePort.findOfferById(offerId);
    }

    @Override
    public List<Offer> getTimetableForBrandAndPartNumber(Integer brandId, String partNumber) {
        // Retrieve all offers from persistence
        List<Offer> offers = offerPersistencePort.findOffersByBrandAndPartNumber(brandId, partNumber);
        // Flatten intervals so that they do not overlap
        return offerDomainService.flattenIntervalsByPriority(offers);
    }
}