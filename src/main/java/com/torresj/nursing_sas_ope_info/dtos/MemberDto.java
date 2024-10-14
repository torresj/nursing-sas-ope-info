package com.torresj.nursing_sas_ope_info.dtos;

public record MemberDto(
        String dni,
        String name,
        String surname,
        String shift,
        float total,
        float op,
        float con,
        int position
) {
}
