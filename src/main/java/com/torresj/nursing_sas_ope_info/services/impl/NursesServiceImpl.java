package com.torresj.nursing_sas_ope_info.services.impl;

import com.torresj.nursing_sas_ope_info.dtos.ExclusionReasons;
import com.torresj.nursing_sas_ope_info.dtos.NurseBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.NurseAreaBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.ScaleDto;
import com.torresj.nursing_sas_ope_info.dtos.NurseOpeResponseDto;
import com.torresj.nursing_sas_ope_info.dtos.ScoreDto;
import com.torresj.nursing_sas_ope_info.entities.NurseBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseCriticsBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseDialysisBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseFamilyBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseGyneBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseMentalHealthBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseNeonatesBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseNuclearBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseOpeDefinitiveEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseOpeProvisionalEntity;
import com.torresj.nursing_sas_ope_info.entities.NursePediatricianBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseSpecialistMentalHealthBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseSurgeryRoomBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.NurseWorkBolsaEntity;
import com.torresj.nursing_sas_ope_info.repositories.NurseBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseCriticsBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseDialysisBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseFamilyBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseGyneBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseMentalHealthBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseNeonatesBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseNuclearBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseOpeDefinitiveRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseOpeProvisionalRepository;
import com.torresj.nursing_sas_ope_info.repositories.NursePediatricianBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseSpecialistMentalHealthBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseSurgeryRoomBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.NurseWorkBolsaRepository;
import com.torresj.nursing_sas_ope_info.services.NursesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

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
    private final NurseDialysisBolsaRepository dialysisBolsaRepository;
    private final NurseFamilyBolsaRepository familyBolsaRepository;
    private final NurseGyneBolsaRepository gyneBolsaRepository;
    private final NurseMentalHealthBolsaRepository mentalHealthBolsaRepository;
    private final NurseNeonatesBolsaRepository neonatesBolsaRepository;
    private final NurseNuclearBolsaRepository nuclearBolsaRepository;
    private final NursePediatricianBolsaRepository pediatricianBolsaRepository;
    private final NurseSpecialistMentalHealthBolsaRepository specialistMentalHealthBolsaRepository;
    private final NurseSurgeryRoomBolsaRepository surgeryRoomBolsaRepository;
    private final NurseWorkBolsaRepository nurseWorkBolsaRepository;

    @Override
    public List<NurseOpeResponseDto> getOpeNurses(String filter) {
        return nurseOpeDefinitiveRepository.findAllBySurnameContainingIgnoreCase(
                StringUtils.stripAccents(filter),
                        Limit.of(100)
                )
                .stream()
                .map(entity -> nursesToResponseDto(findNurseInProvisionalOpeList(entity), entity))
                .toList();
    }

    @Override
    public NurseOpeResponseDto getOpeNurse(long id) {
        var finalNurse = nurseOpeDefinitiveRepository.findById(id).orElseThrow(RuntimeException::new);
        return nursesToResponseDto(
                findNurseInProvisionalOpeList(finalNurse),
                finalNurse
        );
    }

    @Override
    public Set<NurseBolsaDto> getBolsaNurses(String filter){
        return bolsaRepository.findAllBySurnameContainingIgnoreCase(
                StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseAreaBolsaDto> getBolsaCriticsNurses(String filter) {
        return criticsBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseAreaBolsaDto> getBolsaDialysisNurses(String filter) {
        return dialysisBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseBolsaDto> getBolsaFamilyNurses(String filter) {
        return familyBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseBolsaDto> getBolsaGyneNurses(String filter) {
        return gyneBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseAreaBolsaDto> getBolsaMentalHealthNurses(String filter) {
        return mentalHealthBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseAreaBolsaDto> getBolsaNeonatesNurses(String filter) {
        return neonatesBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseAreaBolsaDto> getBolsaNuclearNurses(String filter) {
        return nuclearBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseBolsaDto> getBolsaPediatricianNurses(String filter) {
        return pediatricianBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseBolsaDto> getBolsaSpecificMentalHealthNurses(String filter) {
        return specialistMentalHealthBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseAreaBolsaDto> getBolsaSurgeryRoomNurses(String filter) {
        return surgeryRoomBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NurseBolsaDto> getBolsaWorkNurses(String filter) {
        return nurseWorkBolsaRepository.findAllBySurnameContainingIgnoreCase(
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

    private NurseBolsaDto entityToDto(NurseFamilyBolsaEntity nurse) {
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

    private NurseBolsaDto entityToDto(NurseGyneBolsaEntity nurse) {
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

    private NurseBolsaDto entityToDto(NurseWorkBolsaEntity nurse) {
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

    private NurseBolsaDto entityToDto(NursePediatricianBolsaEntity nurse) {
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

    private NurseBolsaDto entityToDto(NurseSpecialistMentalHealthBolsaEntity nurse) {
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

    private NurseAreaBolsaDto entityToDto(NurseCriticsBolsaEntity nurse) {
        return new NurseAreaBolsaDto(
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

    private NurseAreaBolsaDto entityToDto(NurseDialysisBolsaEntity nurse) {
        return new NurseAreaBolsaDto(
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

    private NurseAreaBolsaDto entityToDto(NurseMentalHealthBolsaEntity nurse) {
        return new NurseAreaBolsaDto(
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

    private NurseAreaBolsaDto entityToDto(NurseNeonatesBolsaEntity nurse) {
        return new NurseAreaBolsaDto(
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

    private NurseAreaBolsaDto entityToDto(NurseNuclearBolsaEntity nurse) {
        return new NurseAreaBolsaDto(
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

    private NurseAreaBolsaDto entityToDto(NurseSurgeryRoomBolsaEntity nurse) {
        return new NurseAreaBolsaDto(
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
