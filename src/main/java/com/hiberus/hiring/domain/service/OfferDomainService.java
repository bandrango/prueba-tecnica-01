package com.hiberus.hiring.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.hiberus.hiring.domain.model.Offer;

/**
 * Domain service containing complex business logic for Offers.
 * Demonstrates a functional approach to flatten overlapping intervals
 * based on priority.
 */
@Component
public class OfferDomainService {
	
	/**
	 * Flattens the list of offers so that their date intervals do not overlap,
	 * applying the correct price for each interval based on priority.
	 *
	 * If two offers overlap:
	 *  - The one with a higher priority prevails in the overlapping range.
	 *  - If they have the same priority and the next offer extends beyond the current offer,
	 *    the intervals are merged.
	 *
	 * @param offers List of offers to be flattened
	 * @return A new list of offers with non-overlapping intervals
	 */
	public List<Offer> flattenIntervalsByPriority(List<Offer> offers) {
	    // Sort offers by start date in ascending order. Use an empty list if offers is null.
	    List<Offer> sortedOffers = Optional.ofNullable(offers)
	            .orElse(Collections.emptyList())
	            .stream()
	            .sorted(Comparator.comparing(Offer::getStartDate))
	            .toList();

	    List<Offer> result = new ArrayList<>();
	    for (Offer currentOffer : sortedOffers) {
	        if (result.isEmpty()) {
	            result.add(currentOffer);
	            continue;
	        }

	        Offer lastOffer = result.get(result.size() - 1);
	        if (currentOffer.getStartDate().isAfter(lastOffer.getEndDate())) {
	            // No overlap: add the current offer.
	            result.add(currentOffer);
	        } else {
	            // Overlap exists; process it.
	            processOverlap(lastOffer, currentOffer, result);
	        }
	    }
	    return result;
	}

	/**
	 * Processes overlapping offers.
	 *
	 * If the current offer has a higher priority than the last offer,
	 * the last offer's endDate is truncated and the current offer is added.
	 * If they have the same priority and the current offer extends further,
	 * the last offer's endDate is updated.
	 *
	 * @param lastOffer the last offer in the result list
	 * @param currentOffer the current offer being processed
	 * @param result the result list of offers
	 */
	private void processOverlap(Offer lastOffer, Offer currentOffer, List<Offer> result) {
	    if (currentOffer.getPriority() > lastOffer.getPriority()) {
	        if (currentOffer.getStartDate().isAfter(lastOffer.getStartDate())) {
	            lastOffer.setEndDate(currentOffer.getStartDate().minusSeconds(1));
	        }
	        // Replace the last offer with the truncated offer, then add the current offer.
	        result.set(result.size() - 1, lastOffer);
	        result.add(currentOffer);
	    } else if (currentOffer.getPriority().equals(lastOffer.getPriority()) &&
	            currentOffer.getEndDate().isAfter(lastOffer.getEndDate())) {
	        // Merge the intervals by extending the last offer's endDate.
	        lastOffer.setEndDate(currentOffer.getEndDate());
	        result.set(result.size() - 1, lastOffer);
	    }
	    // If currentOffer has a lower priority, it is ignored.
	}

}