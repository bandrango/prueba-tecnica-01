package com.hiberus.hiring.infrastructure.adapters.outbound.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.hiberus.hiring.application.port.out.OfferPersistencePort;
import com.hiberus.hiring.domain.model.Offer;
import com.hiberus.hiring.domain.repository.OfferRepository;

import lombok.RequiredArgsConstructor;

/**
 * Concrete implementation of OfferPersistencePort using Spring Data JPA.
 * Converts between domain objects (Offer) and JPA entities (OfferEntity).
 */
@Component
@RequiredArgsConstructor
public class OfferPersistenceAdapter implements OfferPersistencePort {

    private final OfferRepository offerRepository;

    @Override
    public Offer saveOffer(Offer offer) {
        OfferEntity entity = toEntity(offer);
        OfferEntity saved = offerRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteAllOffers() {
        offerRepository.deleteAll();
    }

    @Override
    public void deleteOfferById(Long offerId) {
        offerRepository.deleteById(offerId);
    }

    @Override
    public List<Offer> findAllOffers() {
        return offerRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Offer findOfferById(Long offerId) {
        Optional<OfferEntity> entityOpt = offerRepository.findById(offerId);
        return entityOpt.map(this::toDomain).orElse(null);
    }

    @Override
    public List<Offer> findOffersByBrandAndPartNumber(Integer brandId, String partNumber) {
        return offerRepository.findByBrandIdAndPartnumber(brandId, partNumber)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    // -----------------------
    // Mappers: Entity <-> Domain
    // -----------------------
    private OfferEntity toEntity(Offer offer) {
        return OfferEntity.builder()
                .offerId(offer.getOfferId())
                .brandId(offer.getBrandId())
                .startDate(offer.getStartDate())
                .endDate(offer.getEndDate())
                .priceList(offer.getPriceList())
                .partnumber(offer.getPartnumber())
                .priority(offer.getPriority())
                .price(offer.getPrice())
                .curr(offer.getCurr())
                .build();
    }

    private Offer toDomain(OfferEntity entity) {
        return Offer.builder()
                .offerId(entity.getOfferId())
                .brandId(entity.getBrandId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priceList(entity.getPriceList())
                .partnumber(entity.getPartnumber())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .curr(entity.getCurr())
                .build();
    }
}