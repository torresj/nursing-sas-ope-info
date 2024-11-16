package com.torresj.nursing_sas_ope_info.repositories;

import com.torresj.nursing_sas_ope_info.entities.NurseMentalHealthBolsaEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseMentalHealthBolsaRepository extends CrudRepository<NurseMentalHealthBolsaEntity, Long> {
    List<NurseMentalHealthBolsaEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
}
