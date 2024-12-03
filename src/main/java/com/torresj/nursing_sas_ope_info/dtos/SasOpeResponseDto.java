package com.torresj.nursing_sas_ope_info.dtos;

public record SasOpeResponseDto(
        String dni,
        String name,
        String surname,
        String shift,
        ScoreDto provisionalScore,
        ScoreDto finalScore
) {
}
