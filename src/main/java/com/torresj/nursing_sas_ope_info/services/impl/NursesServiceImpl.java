package com.torresj.nursing_sas_ope_info.services.impl;

import com.torresj.nursing_sas_ope_info.dtos.bolsa.ExclusionReasons;
import com.torresj.nursing_sas_ope_info.dtos.bolsa.NurseBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.bolsa.ScaleDto;
import com.torresj.nursing_sas_ope_info.dtos.ope.NurseOpeResponseDto;
import com.torresj.nursing_sas_ope_info.dtos.ope.ScoreDto;
import com.torresj.nursing_sas_ope_info.entities.nurses.bolsa.NurseBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.ope.NurseOpeDefinitiveEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.ope.NurseOpeProvisionalEntity;
import com.torresj.nursing_sas_ope_info.repositories.nurses.BolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.OpeDefinitiveRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.OpeProvisionalRepository;
import com.torresj.nursing_sas_ope_info.services.NursesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class NursesServiceImpl implements NursesService {

    private final OpeProvisionalRepository opeProvisionalRepository;
    private final OpeDefinitiveRepository opeDefinitiveRepository;
    private final BolsaRepository bolsaRepository;

    @Override
    public List<NurseOpeResponseDto> getOpeNurses(String filter) throws IOException {
        return opeDefinitiveRepository.findAllBySurnameContainingIgnoreCase(
                StringUtils.stripAccents(filter),
                        Limit.of(100)
                )
                .stream()
                .map(entity -> nursesToResponseDto(findNurseInProvisionalOpeList(entity), entity))
                .toList();
    }

    @Override
    public NurseOpeResponseDto getOpeNurse(long id) throws IOException {
        var finalNurse = opeDefinitiveRepository.findById(id).orElseThrow(RuntimeException::new);
        return nursesToResponseDto(
                findNurseInProvisionalOpeList(finalNurse),
                finalNurse
        );
    }

    @Override
    public Set<NurseBolsaDto> getBolsaNurses(String filter) throws IOException {
        return bolsaRepository.findAllBySurnameContainingIgnoreCase(
                StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    private NurseOpeProvisionalEntity findNurseInProvisionalOpeList(NurseOpeDefinitiveEntity nurse) {
        return opeProvisionalRepository.findBySurnameContainingIgnoreCaseAndDni(nurse.getSurname(), nurse.getDni()).orElse(null);
    }

    private NurseOpeResponseDto nursesToResponseDto(NurseOpeProvisionalEntity nurseProvisional, NurseOpeDefinitiveEntity finalNurse) {
        return new NurseOpeResponseDto(
                finalNurse.getDni(),
                finalNurse.getName(),
                finalNurse.getSurname(),
                finalNurse.getShift(),
                nurseProvisional != null
                        ? new ScoreDto(
                                nurseProvisional.getTotal(),
                        nurseProvisional.getOp(),
                        nurseProvisional.getCon(),
                        nurseProvisional.getId())
                        : null,
                new ScoreDto(
                        finalNurse.getTotal(),
                        finalNurse.getOp(),
                        finalNurse.getCon(),
                        finalNurse.getId()
                )
        );
    }

    private NurseBolsaDto entityToDto(NurseBolsaEntity nurse) {
        return new NurseBolsaDto(
                nurse.getDni(),
                nurse.getName(),
                nurse.getSurname(),
                nurse.getShift(),
                nurse.getTreaty(),
                nurse.getStatus(),
                ExclusionReasons.getExclusionReason(nurse.getExclusionCode()),
                new ScaleDto(
                        nurse.getExperience(),
                        nurse.getFormation(),
                        nurse.getOthers(),
                        nurse.getTotal()
                )
        );
    }
}
