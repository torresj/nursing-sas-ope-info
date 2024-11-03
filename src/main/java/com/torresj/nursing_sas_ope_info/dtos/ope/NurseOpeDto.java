package com.torresj.nursing_sas_ope_info.dtos.ope;

public record NurseOpeDto(
        String dni,
        String name,
        String surname,
        String shift,
        ScoreDto score
) {
}
