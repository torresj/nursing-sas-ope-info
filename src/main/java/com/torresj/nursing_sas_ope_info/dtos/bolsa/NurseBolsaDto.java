package com.torresj.nursing_sas_ope_info.dtos.bolsa;

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
