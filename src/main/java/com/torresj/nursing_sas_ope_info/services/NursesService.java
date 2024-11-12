package com.torresj.nursing_sas_ope_info.services;

import com.torresj.nursing_sas_ope_info.dtos.NurseBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.NurseOpeResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface NursesService {
    List<NurseOpeResponseDto> getOpeNurses(String filter) throws IOException;

    NurseOpeResponseDto getOpeNurse(long id) throws IOException;

    Set<NurseBolsaDto> getBolsaNurses(String filter) throws IOException;
}
