package com.torresj.nursing_sas_ope_info.services.impl;

import com.torresj.nursing_sas_ope_info.dtos.bolsa.NurseBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.ope.NurseOpeDto;
import com.torresj.nursing_sas_ope_info.dtos.ope.NurseOpeResponseDto;
import com.torresj.nursing_sas_ope_info.services.NursesService;
import com.torresj.nursing_sas_ope_info.services.SasDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class NursesServiceImpl implements NursesService {

    private final SasDataService sasDataService;

    @Override
    public List<NurseOpeResponseDto> getOpeNurses(String filter) throws IOException {
        var filteredNurses = sasDataService.getOpeDefinitiveListNurses()
                .values()
                .stream()
                .filter(nurse -> nurse.surname().toLowerCase().contains(StringUtils.stripAccents(filter).toLowerCase()))
                .map(nurseOpeDto -> nursesToResponseDto(findNurseInProvisionalOpeList(nurseOpeDto), nurseOpeDto))
                .limit(100)
                .toList();

        return new ArrayList<>(filteredNurses);
    }

    @Override
    public NurseOpeResponseDto getOpeNurse(int id) throws IOException {
        var finalNurse = sasDataService.getOpeDefinitiveListNurses().get(id);
        return nursesToResponseDto(
                findNurseInProvisionalOpeList(finalNurse),
                finalNurse
        );
    }

    @Override
    public Set<NurseBolsaDto> getBolsaNurses(String filter) throws IOException {
        return sasDataService.getBolsaProvisionalListNurses()
                .stream()
                .filter(nurse -> nurse.surname().toLowerCase().contains(StringUtils.stripAccents(filter).toLowerCase()))
                .limit(100)
                .collect(Collectors.toSet());
    }

    private NurseOpeDto findNurseInProvisionalOpeList(NurseOpeDto nurse) {
        try {
            return sasDataService.getOpeProvisionalListNurses()
                    .values()
                    .stream()
                    .filter(nurseOpeDto ->
                            nurseOpeDto.surname().equalsIgnoreCase(nurse.surname()) &&
                                    nurseOpeDto.dni().equals(nurse.dni()))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private NurseOpeResponseDto nursesToResponseDto(NurseOpeDto nurseProvisional, NurseOpeDto finalNurse) {
        return new NurseOpeResponseDto(
                finalNurse.dni(),
                finalNurse.name(),
                finalNurse.surname(),
                finalNurse.shift(),
                nurseProvisional != null? nurseProvisional.score() : null,
                finalNurse.score()
        );
    }
}
