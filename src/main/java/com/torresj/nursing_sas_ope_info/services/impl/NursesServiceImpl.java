package com.torresj.nursing_sas_ope_info.services.impl;

import com.torresj.nursing_sas_ope_info.dtos.ExclusionReasons;
import com.torresj.nursing_sas_ope_info.dtos.NurseAreaBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.SasBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.SasOpeResponseDto;
import com.torresj.nursing_sas_ope_info.dtos.ScaleDto;
import com.torresj.nursing_sas_ope_info.dtos.ScoreDto;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseCriticsBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseDialysisBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseFamilyBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseGyneBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseMentalHealthBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseNeonatesBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseNuclearBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseOpeDefinitiveEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseOpeProvisionalEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NursePediatricianBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseSpecialistMentalHealthBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseSurgeryRoomBolsaEntity;
import com.torresj.nursing_sas_ope_info.entities.nurses.NurseWorkBolsaEntity;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseCriticsBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseDialysisBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseFamilyBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseGyneBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseMentalHealthBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseNeonatesBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseNuclearBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseOpeDefinitiveRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseOpeProvisionalRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NursePediatricianBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseSpecialistMentalHealthBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseSurgeryRoomBolsaRepository;
import com.torresj.nursing_sas_ope_info.repositories.nurses.NurseWorkBolsaRepository;
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
    public List<SasOpeResponseDto> getOpeNurses(String filter) {
        return nurseOpeDefinitiveRepository.findAllBySurnameContainingIgnoreCase(
                StringUtils.stripAccents(filter),
                        Limit.of(100)
                )
                .stream()
                .map(entity -> nursesToResponseDto(findNurseInProvisionalOpeList(entity), entity))
                .toList();
    }

    @Override
    public SasOpeResponseDto getOpeNurse(long id) {
        var finalNurse = nurseOpeDefinitiveRepository.findById(id).orElseThrow(RuntimeException::new);
        return nursesToResponseDto(
                findNurseInProvisionalOpeList(finalNurse),
                finalNurse
        );
    }

    @Override
    public Set<SasBolsaDto> getBolsaNurses(String filter){
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
    public Set<SasBolsaDto> getBolsaFamilyNurses(String filter) {
        return familyBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SasBolsaDto> getBolsaGyneNurses(String filter) {
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
    public Set<SasBolsaDto> getBolsaPediatricianNurses(String filter) {
        return pediatricianBolsaRepository.findAllBySurnameContainingIgnoreCase(
                        StringUtils.stripAccents(filter),
                        Limit.of(100))
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SasBolsaDto> getBolsaSpecificMentalHealthNurses(String filter) {
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
    public Set<SasBolsaDto> getBolsaWorkNurses(String filter) {
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

    private SasOpeResponseDto nursesToResponseDto(
            NurseOpeProvisionalEntity nurseProvisional,
            NurseOpeDefinitiveEntity finalNurse
    ) {
        return new SasOpeResponseDto(
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

    private SasBolsaDto entityToDto(NurseBolsaEntity nurse) {
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

    private SasBolsaDto entityToDto(NurseFamilyBolsaEntity nurse) {
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

    private SasBolsaDto entityToDto(NurseGyneBolsaEntity nurse) {
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

    private SasBolsaDto entityToDto(NurseWorkBolsaEntity nurse) {
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

    private SasBolsaDto entityToDto(NursePediatricianBolsaEntity nurse) {
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

    private SasBolsaDto entityToDto(NurseSpecialistMentalHealthBolsaEntity nurse) {
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
