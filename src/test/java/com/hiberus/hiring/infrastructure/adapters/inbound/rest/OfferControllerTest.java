package com.hiberus.hiring.infrastructure.adapters.inbound.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hiberus.hiring.application.port.in.OfferUseCase;
import com.hiberus.hiring.domain.model.Offer;
import com.hiberus.hiring.infrastructure.adapters.inbound.controller.OfferController;
import com.hiberus.hiring.infrastructure.adapters.inbound.controller.dto.OfferDto;

@ExtendWith(MockitoExtension.class)	
class OfferControllerTest {

	@Mock
    private OfferUseCase offerUseCase;

    @InjectMocks
    private OfferController controller;

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
    void testCreateNewOffer() {
        // No return, just ensure the method calls the use case
        controller.createNewOffer(new OfferDto(sampleOffer));
        verify(offerUseCase, times(1)).createOffer(any(Offer.class));
    }

    @Test
    void testDeleteAllOffers() {
        controller.deleteAllOffers();
        verify(offerUseCase, times(1)).deleteAllOffers();
    }

    @Test
    void testDeleteOfferById() {
        controller.deleteOfferById(10L);
        verify(offerUseCase, times(1)).deleteOfferById(10L);
    }

    @Test
    void testGetAllOffers() {
        when(offerUseCase.getAllOffers()).thenReturn(List.of(sampleOffer));
        List<OfferDto> result = controller.getAllOffers();
        assertEquals(1, result.size());
        assertEquals(sampleOffer.getOfferId(), result.get(0).getOfferId());
    }

    @Test
    void testGetOfferById_found() {
        when(offerUseCase.getOfferById(1L)).thenReturn(sampleOffer);
        ResponseEntity<OfferDto> response = controller.getOfferById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getOfferId());
    }

    @Test
    void testGetOfferById_notFound() {
        when(offerUseCase.getOfferById(999L)).thenReturn(null);
        ResponseEntity<OfferDto> response = controller.getOfferById(999L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
