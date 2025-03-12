package com.hiberus.hiring.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiberus.hiring.domain.model.Offer;

class OfferDomainServiceTest {
	
	private OfferDomainService service;

    @BeforeEach
    void setUp() {
        service = new OfferDomainService();
    }

    @Test
    void testFlattenIntervals_NullInput_ReturnsEmptyList() {
        List<Offer> result = service.flattenIntervalsByPriority(null);
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for null input");
    }

    @Test
    void testFlattenIntervals_EmptyInput_ReturnsEmptyList() {
        List<Offer> result = service.flattenIntervalsByPriority(Collections.emptyList());
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for empty input");
    }

    @Test
    void testFlattenIntervals_SingleOffer_ReturnsSameOffer() {
        Offer offer = Offer.builder()
                .offerId(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 10, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 12, 0))
                .priority(1)
                .build();

        List<Offer> result = service.flattenIntervalsByPriority(List.of(offer));
        assertEquals(1, result.size(), "Single offer should be returned as is");
        assertEquals(offer, result.get(0));
    }

    @Test
    void testFlattenIntervals_NoOverlap_TwoOffers() {
        Offer offer1 = Offer.builder()
                .offerId(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 10, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 11, 0))
                .priority(1)
                .build();
        Offer offer2 = Offer.builder()
                .offerId(2L)
                .startDate(LocalDateTime.of(2020, 6, 14, 11, 5))
                .endDate(LocalDateTime.of(2020, 6, 14, 12, 0))
                .priority(2)
                .build();

        List<Offer> result = service.flattenIntervalsByPriority(List.of(offer1, offer2));
        assertEquals(2, result.size(), "Should return both offers when there is no overlap");
        assertEquals(offer1, result.get(0));
        assertEquals(offer2, result.get(1));
    }

    @Test
    void testFlattenIntervals_Overlap_HigherPriority() {
        // offer2 has a higher priority than offer1 and overlaps
        Offer offer1 = Offer.builder()
                .offerId(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 10, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 12, 0))
                .priority(0)
                .build();
        Offer offer2 = Offer.builder()
                .offerId(2L)
                .startDate(LocalDateTime.of(2020, 6, 14, 11, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 13, 0))
                .priority(1)
                .build();

        List<Offer> result = service.flattenIntervalsByPriority(List.of(offer1, offer2));

        // Expect offer1's endDate to be truncated to offer2's startDate minus one second, and offer2 added.
        assertEquals(2, result.size(), "Result should contain 2 offers after processing overlap");
        Offer truncatedOffer = result.get(0);
        assertEquals(offer2.getStartDate().minusSeconds(1), truncatedOffer.getEndDate(), 
                "Offer1 should be truncated to one second before offer2's start");
        assertEquals(offer2, result.get(1), "Offer2 should be added after truncating offer1");
    }

    @Test
    void testFlattenIntervals_Overlap_SamePriority_MergeIntervals() {
        // Both offers have the same priority, and offer2 extends beyond offer1.
        Offer offer1 = Offer.builder()
                .offerId(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 10, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 12, 0))
                .priority(1)
                .build();
        Offer offer2 = Offer.builder()
                .offerId(2L)
                .startDate(LocalDateTime.of(2020, 6, 14, 11, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 13, 0))
                .priority(1)
                .build();

        List<Offer> result = service.flattenIntervalsByPriority(List.of(offer1, offer2));

        // They should merge into one offer
        assertEquals(1, result.size(), "Offers with the same priority should merge into a single interval");
        Offer mergedOffer = result.get(0);
        assertEquals(offer1.getStartDate(), mergedOffer.getStartDate(), "Merged offer should start at offer1's start time");
        assertEquals(offer2.getEndDate(), mergedOffer.getEndDate(), "Merged offer should end at offer2's end time");
    }

    @Test
    void testFlattenIntervals_Overlap_LowerPriorityIgnored() {
        // offer2 has lower priority than offer1, so it should be ignored.
        Offer offer1 = Offer.builder()
                .offerId(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 10, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 12, 0))
                .priority(1)
                .build();
        Offer offer2 = Offer.builder()
                .offerId(2L)
                .startDate(LocalDateTime.of(2020, 6, 14, 11, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 13, 0))
                .priority(0)
                .build();

        List<Offer> result = service.flattenIntervalsByPriority(List.of(offer1, offer2));
        // Only offer1 should remain
        assertEquals(1, result.size(), "Offer with lower priority should be ignored");
        assertEquals(offer1, result.get(0));
    }

    @Test
    void testFlattenIntervals_MultipleOffers() {
        // Test with three offers: some overlapping and some non-overlapping
        Offer offer1 = Offer.builder()
                .offerId(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 9, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 11, 0))
                .priority(1)
                .build();
        Offer offer2 = Offer.builder()
                .offerId(2L)
                .startDate(LocalDateTime.of(2020, 6, 14, 10, 30))
                .endDate(LocalDateTime.of(2020, 6, 14, 12, 0))
                .priority(2)
                .build();
        Offer offer3 = Offer.builder()
                .offerId(3L)
                .startDate(LocalDateTime.of(2020, 6, 14, 12, 30))
                .endDate(LocalDateTime.of(2020, 6, 14, 13, 30))
                .priority(1)
                .build();

        List<Offer> result = service.flattenIntervalsByPriority(List.of(offer1, offer2, offer3));
        // Expected result: offer1 is truncated by offer2, then offer3 is added as there is no overlap with offer2.
        assertEquals(3, result.size(), "Should contain three offers after processing multiple intervals");
        
        // Check that offer1 is truncated correctly.
        Offer truncatedOffer1 = result.get(0);
        assertEquals(offer2.getStartDate().minusSeconds(1), truncatedOffer1.getEndDate(),
                "Offer1 should be truncated to one second before offer2's start");
        
        // Ensure offer2 and offer3 are included as-is.
        assertEquals(offer2, result.get(1));
        assertEquals(offer3, result.get(2));
    }

}