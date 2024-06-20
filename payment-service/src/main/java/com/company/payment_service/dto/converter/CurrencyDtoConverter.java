package com.company.payment_service.dto.converter;

import com.company.payment_service.dto.enums.CurrencyDto;
import com.company.payment_service.model.enums.Currency;

public class CurrencyDtoConverter {

    public static CurrencyDto convertToDto(Currency orderStatus) {
        return switch (orderStatus) {
            case AZN -> CurrencyDto.AZN;
            case TRY -> CurrencyDto.TRY;
            case USD -> CurrencyDto.USD;
            case EUR -> CurrencyDto.EUR;
        };
    }
}
