package com.torresj.nursing_sas_ope_info.dtos.ope;

public record NurseOpeResponseDto(
        String dni,
        String name,
        String surname,
        String shift,
        ScoreDto provisionalScore,
        ScoreDto finalScore
) {
}