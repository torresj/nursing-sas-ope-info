package com.torresj.nursing_sas_ope_info.services;

import com.torresj.nursing_sas_ope_info.dtos.SasBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.NurseAreaBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.SasOpeResponseDto;

import java.util.List;
import java.util.Set;

public interface NursesService {
    List<SasOpeResponseDto> getOpeNurses(String filter);

    SasOpeResponseDto getOpeNurse(long id);

    Set<SasBolsaDto> getBolsaNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaCriticsNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaDialysisNurses(String filter);

    Set<SasBolsaDto> getBolsaFamilyNurses(String filter);

    Set<SasBolsaDto> getBolsaGyneNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaMentalHealthNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaNeonatesNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaNuclearNurses(String filter);

    Set<SasBolsaDto> getBolsaPediatricianNurses(String filter);

    Set<SasBolsaDto> getBolsaSpecificMentalHealthNurses(String filter);

    Set<NurseAreaBolsaDto> getBolsaSurgeryRoomNurses(String filter);

    Set<SasBolsaDto> getBolsaWorkNurses(String filter);
}
