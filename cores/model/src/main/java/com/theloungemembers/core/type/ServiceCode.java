package com.theloungemembers.core.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceCode implements BaseCodeEnum {

    LOUNGE("lounge", "공항 라운지"),
    DINING("dining", "공항 다이닝"),
    AIRPORT_EXPRESS("airport_express", "공항 직통열차"),
    AIRPORT_LIMOUSINE("airport_limousine", "K공항리무진"),
    GOLF_DRIVING_RANGE("golf_driving_range", "골프연습장"),
    ITCHA("itcha", "잇차"),
    MOSILER("mosiler", "모시러"),
    AIRPORT_HOTEL("airport_hotel", "다락휴 호텔"),
    HOTEL_VALET("hotel_valet", "호텔 발렛"),
    AIRPORT_VALET("airport_valet", "공항 발렛"),
    MATINA_GOLD("matina_gold", "마티나 골드"),
    AIRPORT_COFFEE("airport_coffee", "공항 커피"),
    USIM_ESIM("usim_esim", "글로벌 One SIM"),
    HOTEL_BUFFET("hotel_buffet", "특급호텔 뷔페");

    private final String code;
    private final String description;

    public static ServiceCode of(String code) {
        for (ServiceCode status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }

        return null;
    }
}