package com.torresj.nursing_sas_ope_info.services.impl;

import com.torresj.nursing_sas_ope_info.dtos.ExclusionReasons;
import com.torresj.nursing_sas_ope_info.dtos.SasBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.SasOpeResponseDto;
import com.torresj.nursing_sas_ope_info.dtos.ScaleDto;
import com.torresj.nursing_sas_ope_info.dtos.ScoreDto;
import com.torresj.nursing_sas_ope_info.entities.tcae.TcaeBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.tcae.TcaeOpeDefinitiveEntity;
import com.torresj.nursing_sas_ope_info.entities.tcae.TcaeOpeProvisionalEntity;
import com.torresj.nursing_sas_ope_info.repositories.tcae.TcaeBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.tcae.TcaeOpeDefinitiveRepository;
import com.torresj.nursing_sas_ope_info.repositories.tcae.TcaeOpeProvisionalRepository;
import com.torresj.nursing_sas_ope_info.services.TcaesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TcaesServiceImpl implements TcaesService {

    private final TcaeOpeDefinitiveRepository tcaeOpeDefinitiveRepository;
    private final TcaeOpeProvisionalRepository tcaeOpeProvisionalRepository;
    private final TcaeBolsaRepository tcaeBolsaRepository;

    @Override
    public List<SasOpeResponseDto> getOpeTcaes(String filter) {
        return tcaeOpeDefinitiveRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100)
                )
                .stream()
                .map(entity -> tcaeToResponseDto(findTcaeInProvisionalOpeList(entity), entity))
                .toList();
    }

    @Override
    public SasOpeResponseDto getOpeTcae(long id) {
        var finalTcae = tcaeOpeDefinitiveRepository.findById(id).orElseThrow(RuntimeException::new);
        return tcaeToResponseDto(
                findTcaeInProvisionalOpeList(finalTcae),
                finalTcae
        );
    }

    @Override
    public Set<SasBolsaDto> getBolsaTcaes(String filter) {
        return tcaeBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    private SasOpeResponseDto tcaeToResponseDto(
            TcaeOpeProvisionalEntity tcaeProvisional,
            TcaeOpeDefinitiveEntity finalTcae
    ) {
        return new SasOpeResponseDto(
                finalTcae.getDni(),
                finalTcae.getName(),
                finalTcae.getSurname(),
                finalTcae.getShift(),
                tcaeProvisional != null
                        ? new ScoreDto(
                        tcaeProvisional.getTotal(),
                        tcaeProvisional.getOp(),
                        tcaeProvisional.getCon(),
                        tcaeProvisional.getPosition())
                        : null,
                new ScoreDto(
                        finalTcae.getTotal(),
                        finalTcae.getOp(),
                        finalTcae.getCon(),
                        finalTcae.getPosition()
                )
        );
    }

    private TcaeOpeProvisionalEntity findTcaeInProvisionalOpeList(TcaeOpeDefinitiveEntity tcae) {
        return tcaeOpeProvisionalRepository.findBySurnameContainingIgnoreCaseAndDni(
                tcae.getSurname(),
                tcae.getDni()
        ).orElse(null);
    }

    private SasBolsaDto entityToDto(TcaeBolsaEntity nurse) {
        return new SasBolsaDto(
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
