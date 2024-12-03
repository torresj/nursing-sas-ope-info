package com.torresj.nursing_sas_ope_info.services;

import com.torresj.nursing_sas_ope_info.dtos.SasBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.SasOpeResponseDto;

import java.util.List;
import java.util.Set;

public interface TcaesService {
    List<SasOpeResponseDto> getOpeTcaes(String filter);

    SasOpeResponseDto getOpeTcae(long id);

    Set<SasBolsaDto> getBolsaTcaes(String filter);
}
