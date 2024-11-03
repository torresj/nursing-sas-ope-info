package com.torresj.nursing_sas_ope_info.services;

import com.torresj.nursing_sas_ope_info.dtos.bolsa.NurseBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.ope.NurseOpeDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SasDataService {
    Map<Integer, NurseOpeDto> getOpeDefinitiveListNurses() throws IOException;
    Map<Integer, NurseOpeDto> getOpeProvisionalListNurses() throws IOException;
    Set<NurseBolsaDto> getBolsaProvisionalListNurses() throws IOException;
}
