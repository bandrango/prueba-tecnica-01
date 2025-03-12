package com.hiberus.hiring.infrastructure.adapters.inbound.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hiberus.hiring.domain.model.Offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Use this POJO for offer service end point responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferDto {

	private Long offerId;
    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private String partnumber;
    private Integer priority;
    private BigDecimal price;
    private String curr;

    /**
     * Constructs this DTO from a domain Offer.
     */
    public OfferDto(Offer offer) {
        this.offerId = offer.getOfferId();
        this.brandId = offer.getBrandId();
        this.startDate = offer.getStartDate();
        this.endDate = offer.getEndDate();
        this.priceList = offer.getPriceList();
        this.partnumber = offer.getPartnumber();
        this.priority = offer.getPriority();
        this.price = offer.getPrice();
        this.curr = offer.getCurr();
    }

    /**
     * Converts this DTO into a domain Offer object.
     */
    public Offer toDomain() {
        return Offer.builder()
                .offerId(this.offerId)
                .brandId(this.brandId)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .priceList(this.priceList)
                .partnumber(this.partnumber)
                .priority(this.priority)
                .price(this.price)
                .curr(this.curr)
                .build();
    }

}