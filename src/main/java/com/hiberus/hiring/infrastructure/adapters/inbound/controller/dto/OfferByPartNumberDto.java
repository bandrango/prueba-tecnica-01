package com.hiberus.hiring.infrastructure.adapters.inbound.controller.dto;

import com.hiberus.hiring.domain.model.Offer;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO representing the flattened intervals for a specific product.
 * "from" - start date/time
 * "until" - end date/time
 * "price" - final price
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferByPartNumberDto {

    private LocalDateTime from;
    private LocalDateTime until;
    private BigDecimal price;

    /**
     * Constructs this DTO from a domain Offer.
     */
    public OfferByPartNumberDto(Offer offer) {
        this.from = offer.getStartDate();
        this.until = offer.getEndDate();
        this.price = offer.getPrice();
    }
}