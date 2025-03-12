package com.hiberus.hiring.infrastructure.adapters.outbound.persistence;

import com.hiberus.hiring.domain.model.Offer;
import com.hiberus.hiring.domain.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferPersistenceAdapterTest {

	@Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferPersistenceAdapter adapter;

    private Offer sampleOffer;
    private OfferEntity sampleEntity;

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

        sampleEntity = OfferEntity.builder()
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
    void testSaveOffer() {
        // Arrange
        when(offerRepository.save(any(OfferEntity.class))).thenReturn(sampleEntity);

        // Act
        Offer result = adapter.saveOffer(sampleOffer);

        // Assert
        verify(offerRepository, times(1)).save(any(OfferEntity.class));
        assertEquals(sampleOffer.getOfferId(), result.getOfferId());
        assertEquals(sampleOffer.getPrice(), result.getPrice());
    }

    @Test
    void testDeleteAllOffers() {
        // Act
        adapter.deleteAllOffers();

        // Assert
        verify(offerRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteOfferById() {
        // Act
        adapter.deleteOfferById(10L);

        // Assert
        verify(offerRepository, times(1)).deleteById(10L);
    }

    @Test
    void testFindAllOffers() {
        // Arrange
        when(offerRepository.findAll()).thenReturn(List.of(sampleEntity));

        // Act
        List<Offer> offers = adapter.findAllOffers();

        // Assert
        verify(offerRepository, times(1)).findAll();
        assertEquals(1, offers.size());
        assertEquals(1L, offers.get(0).getOfferId());
    }

    @Test
    void testFindOfferById_exists() {
        // Arrange
        when(offerRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));

        // Act
        Offer found = adapter.findOfferById(1L);

        // Assert
        verify(offerRepository, times(1)).findById(1L);
        assertNotNull(found);
        assertEquals(1L, found.getOfferId());
    }

    @Test
    void testFindOfferById_notExists() {
        // Arrange
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Offer found = adapter.findOfferById(999L);

        // Assert
        verify(offerRepository, times(1)).findById(999L);
        assertNull(found);
    }

    @Test
    void testFindOffersByBrandAndPartNumber() {
        // Arrange
        when(offerRepository.findByBrandIdAndPartnumber(1, "0001002"))
                .thenReturn(List.of(sampleEntity));

        // Act
        List<Offer> result = adapter.findOffersByBrandAndPartNumber(1, "0001002");

        // Assert
        verify(offerRepository, times(1)).findByBrandIdAndPartnumber(1, "0001002");
        assertEquals(1, result.size());
        assertEquals("0001002", result.get(0).getPartnumber());
    }
}
