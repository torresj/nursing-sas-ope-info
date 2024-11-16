package com.torresj.nursing_sas_ope_info.repositories;

import com.torresj.nursing_sas_ope_info.entities.NurseFamilyBolsaEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseFamilyBolsaRepository extends CrudRepository<NurseFamilyBolsaEntity, Long> {
    List<NurseFamilyBolsaEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
}
