package com.torresj.nursing_sas_ope_info.dtos;

import com.torresj.nursing_sas_ope_info.entities.Status;

import java.util.List;

public record NurseCriticsBolsaDto(
        String dni,
        String name,
        String surname,
        String shift,
        String treaty,
        Status general,
        Status specific,
        List<ExclusionReasonDto> exclusionReasons,
        ScaleDto scale
) {
}
