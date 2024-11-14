package com.torresj.nursing_sas_ope_info.services.impl;

import com.torresj.nursing_sas_ope_info.dtos.ExclusionReasons;
import com.torresj.nursing_sas_ope_info.dtos.NurseBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.NurseCriticsBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.ScaleDto;
import com.torresj.nursing_sas_ope_info.dtos.NurseOpeResponseDto;
import com.torresj.nursing_sas_ope_info.dtos.ScoreDto;
import com.torresj.nursing_sas_ope_info.entities.NurseBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseCriticsBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseOpeDefinitiveEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseOpeProvisionalEntity;
import com.torresj.nursing_sas_ope_info.repositories.NurseBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseCriticsBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseOpeDefinitiveRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseOpeProvisionalRepository;
import com.torresj.nursing_sas_ope_info.services.NursesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class NursesServiceImpl implements NursesService {

    private final NurseOpeProvisionalRepository nurseOpeProvisionalRepository;
    private final NurseOpeDefinitiveRepository nurseOpeDefinitiveRepository;
    private final NurseBolsaRepository bolsaRepository;
    private final NurseCriticsBolsaRepository criticsBolsaRepository;

    @Override
    public List<NurseOpeResponseDto> getOpeNurses(String filter) throws IOException {
        return nurseOpeDefinitiveRepository.findAllBySurnameContainingIgnoreCase(
                StringUtils.stripAccents(filter),
                        Limit.of(100)
                )
                .stream()
                .map(entity -> nursesToResponseDto(findNurseInProvisionalOpeList(entity), entity))
                .toList();
    }

    @Override
    public NurseOpeResponseDto getOpeNurse(long id) throws IOException {
        var finalNurse = nurseOpeDefinitiveRepository.findById(id).orElseThrow(RuntimeException::new);
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

    @Override
    public Set<NurseCriticsBolsaDto> getBolsaCriticsNurses(String filter) throws IOException {
        return criticsBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    private NurseOpeProvisionalEntity findNurseInProvisionalOpeList(NurseOpeDefinitiveEntity nurse) {
        return nurseOpeProvisionalRepository.findBySurnameContainingIgnoreCaseAndDni(
                nurse.getSurname(),
                nurse.getDni()
                ).orElse(null);
    }

    private NurseOpeResponseDto nursesToResponseDto(
            NurseOpeProvisionalEntity nurseProvisional,
            NurseOpeDefinitiveEntity finalNurse
    ) {
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
                        nurseProvisional.getPosition())
                        : null,
                new ScoreDto(
                        finalNurse.getTotal(),
                        finalNurse.getOp(),
                        finalNurse.getCon(),
                        finalNurse.getPosition()
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

    private NurseCriticsBolsaDto entityToDto(NurseCriticsBolsaEntity nurse) {
        return new NurseCriticsBolsaDto(
                nurse.getDni(),
                nurse.getName(),
                nurse.getSurname(),
                nurse.getShift(),
                nurse.getTreaty(),
                nurse.getGeneral_admission(),
                nurse.getSpecific_admission(),
                Arrays.stream(nurse.getExclusionCodes()
                        .split(","))
                        .map(ExclusionReasons::getExclusionReason)
                        .collect(Collectors.toList()),
                new ScaleDto(
                        nurse.getExperience(),
                        nurse.getFormation(),
                        nurse.getOthers(),
                        nurse.getTotal()
                )
        );
    }
}
