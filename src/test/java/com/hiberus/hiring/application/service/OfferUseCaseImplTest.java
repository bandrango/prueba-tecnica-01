package com.hiberus.hiring.application.service;

import com.hiberus.hiring.application.port.out.OfferPersistencePort;
import com.hiberus.hiring.domain.model.Offer;
import com.hiberus.hiring.domain.service.OfferDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferUseCaseImplTest {
	
	@Mock
    private OfferPersistencePort offerPersistencePort;

    @Mock
    private OfferDomainService offerDomainService;

    @InjectMocks
    private OfferUseCaseImpl offerUseCase;

    private Offer sampleOffer;

    @BeforeEach
    void setUp() {
        sampleOffer = Offer.builder()
                .offerId(1L)
                .brandId(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 14, 59, 59))
                .priceList(1)
                .partnumber("0001002")
                .priority(0)
                .price(new BigDecimal("35.50"))
                .curr("EUR")
                .build();
    }

    @Test
    void testCreateOffer() {
        when(offerPersistencePort.saveOffer(sampleOffer)).thenReturn(sampleOffer);

        Offer created = offerUseCase.createOffer(sampleOffer);

        verify(offerPersistencePort, times(1)).saveOffer(sampleOffer);
        assertEquals(sampleOffer.getOfferId(), created.getOfferId());
    }

    @Test
    void testDeleteAllOffers() {
        // Act
        offerUseCase.deleteAllOffers();

        // Assert
        verify(offerPersistencePort, times(1)).deleteAllOffers();
    }

    @Test
    void testDeleteOfferById() {
        // Act
        offerUseCase.deleteOfferById(10L);

        // Assert
        verify(offerPersistencePort, times(1)).deleteOfferById(10L);
    }

    @Test
    void testGetAllOffers() {
        when(offerPersistencePort.findAllOffers()).thenReturn(List.of(sampleOffer));

        List<Offer> result = offerUseCase.getAllOffers();

        verify(offerPersistencePort, times(1)).findAllOffers();
        assertEquals(1, result.size());
        assertEquals(sampleOffer.getOfferId(), result.get(0).getOfferId());
    }

    @Test
    void testGetOfferById_exists() {
        when(offerPersistencePort.findOfferById(1L)).thenReturn(sampleOffer);

        Offer found = offerUseCase.getOfferById(1L);

        verify(offerPersistencePort, times(1)).findOfferById(1L);
        assertNotNull(found);
        assertEquals(1L, found.getOfferId());
    }

    @Test
    void testGetOfferById_notExists() {
        when(offerPersistencePort.findOfferById(anyLong())).thenReturn(null);

        Offer found = offerUseCase.getOfferById(999L);

        verify(offerPersistencePort, times(1)).findOfferById(999L);
        assertNull(found, "Offer should be null if it does not exist");
    }

    @Test
    void testGetTimetableForBrandAndPartNumber() {
        when(offerPersistencePort.findOffersByBrandAndPartNumber(1, "0001002"))
                .thenReturn(List.of(sampleOffer));
        when(offerDomainService.flattenIntervalsByPriority(anyList()))
                .thenReturn(List.of(sampleOffer));

        List<Offer> timetable = offerUseCase.getTimetableForBrandAndPartNumber(1, "0001002");

        verify(offerPersistencePort, times(1)).findOffersByBrandAndPartNumber(1, "0001002");
        verify(offerDomainService, times(1)).flattenIntervalsByPriority(anyList());
        assertEquals(1, timetable.size());
        assertEquals(sampleOffer.getOfferId(), timetable.get(0).getOfferId());
    }

}