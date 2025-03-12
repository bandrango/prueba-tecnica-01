package com.hiberus.hiring.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domain entity representing an Offer.
 * This class should remain free of infrastructure concerns (like JPA annotations).
 * 
 * Applying Lombok annotations to reduce boilerplate.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

	private Long offerId;
    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private String partnumber;
    private Integer priority;
    private BigDecimal price;
    private String curr;
}
