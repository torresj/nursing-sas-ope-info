package com.torresj.nursing_sas_ope_info.dtos;

public record SasOpeDto(
        String dni,
        String name,
        String surname,
        String shift,
        ScoreDto score
) {
}
