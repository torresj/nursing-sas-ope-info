package com.torresj.nursing_sas_ope_info.dtos;

import com.torresj.nursing_sas_ope_info.entities.Status;

public record NurseBolsaDto(
        String dni,
        String name,
        String surname,
        String shift,
        String treaty,
        Status status,
        ExclusionReasonDto exclusionReason,
        ScaleDto scale
) {
}
