package com.hiberus.hiring.infrastructure.adapters.inbound.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hiberus.hiring.application.port.in.OfferUseCase;
import com.hiberus.hiring.domain.model.Offer;
import com.hiberus.hiring.infrastructure.adapters.inbound.controller.dto.OfferByPartNumberDto;
import com.hiberus.hiring.infrastructure.adapters.inbound.controller.dto.OfferDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * You can change this controller but please do not change ends points signatures & payloads.
 */
/**
 * REST Controller for managing Offers. Implements endpoints for creating,
 * deleting, and querying offers.
 */
@RestController
@RequestMapping("/product-pricing/api/v1")
@RequiredArgsConstructor
@Tag(name = "Offer Management", description = "Operations for managing Offers")
public class OfferController {

	private final OfferUseCase offerUseCase;

	/**
	 * (POST) /offer Creates a new offer in the system.
	 *
	 * @param offerDto the offer data received in JSON format
	 */
	@Operation(summary = "Create a new offer")
	@PostMapping(value = "/offer", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewOffer(@RequestBody @Valid OfferDto offerDto) {
		// Convert DTO to domain object and delegate creation
		offerUseCase.createOffer(offerDto.toDomain());
	}

	/**
	 * (DELETE) /offer Deletes all offers from the system.
	 */
	@Operation(summary = "Delete all offers")
	@DeleteMapping("/offer")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAllOffers() {
		offerUseCase.deleteAllOffers();
	}

	/**
	 * (DELETE) /offer/{id} Deletes a specific offer by its ID.
	 *
	 * @param id the ID of the offer to delete
	 */
	@Operation(summary = "Delete an offer by ID")
	@DeleteMapping("/offer/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteOfferById(@PathVariable("id") Long id) {
		offerUseCase.deleteOfferById(id);
	}

	/**
	 * (GET) /offer Retrieves all offers from the system.
	 *
	 * @return a list of OfferDto objects
	 */
	@Operation(summary = "Get all offers")
	@GetMapping("/offer")
	@ResponseStatus(HttpStatus.OK)
	public List<OfferDto> getAllOffers() {
		return offerUseCase.getAllOffers()
				.stream()
				.map(OfferDto::new)
				.toList();
	}

	/**
	 * (GET) /offer/{id} Retrieves a specific offer by its ID.
	 *
	 * @param id the ID of the offer to retrieve
	 * @return the OfferDto representing the offer, or a 404 if not found
	 */
	@Operation(summary = "Get an offer by ID")
	@GetMapping("/offer/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<OfferDto> getOfferById(@PathVariable("id") Long id) {
		Offer offer = offerUseCase.getOfferById(id);
		if (offer != null) {
			return ResponseEntity.ok(new OfferDto(offer));
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * (GET) /brand/{brandId}/partnumber/{partnumber}/offer Retrieves a flattened
	 * timetable (non-overlapping intervals) for a given brand and partnumber.
	 *
	 * @param brandId    the brand identifier
	 * @param partnumber the product partnumber
	 * @return a list of OfferByPartNumberDto representing the flattened intervals
	 */
	@Operation(summary = "Get timetable (flattened intervals) for a product")
	@GetMapping("/brand/{brandId}/partnumber/{partnumber}/offer")
	@ResponseStatus(HttpStatus.OK)
	public List<OfferByPartNumberDto> getOfferByPartNumber(@PathVariable("brandId") Integer brandId,
			@PathVariable("partnumber") String partnumber) {
		List<Offer> flattenedOffers = offerUseCase.getTimetableForBrandAndPartNumber(brandId, partnumber);
		return flattenedOffers.stream()
				.map(OfferByPartNumberDto::new)
				.toList();
	}
}
