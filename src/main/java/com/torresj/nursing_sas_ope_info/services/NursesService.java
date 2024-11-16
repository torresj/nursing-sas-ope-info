package com.torresj.nursing_sas_ope_info.services;

import com.torresj.nursing_sas_ope_info.dtos.NurseBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.NurseAreaBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.NurseOpeResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface NursesService {
    List<NurseOpeResponseDto> getOpeNurses(String filter);

    NurseOpeResponseDto getOpeNurse(long id);

    Set<NurseBolsaDto> getBolsaNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaCriticsNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaDialysisNurses(String filter);

    Set<NurseBolsaDto> getBolsaFamilyNurses(String filter);

    Set<NurseBolsaDto> getBolsaGyneNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaMentalHealthNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaNeonatesNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaNuclearNurses(String filter);

    Set<NurseBolsaDto> getBolsaPediatricianNurses(String filter);

    Set<NurseBolsaDto> getBolsaSpecificMentalHealthNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaSurgeryRoomNurses(String filter);

    Set<NurseBolsaDto> getBolsaWorkNurses(String filter);
}
