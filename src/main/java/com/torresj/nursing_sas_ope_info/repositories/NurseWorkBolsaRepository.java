package com.torresj.nursing_sas_ope_info.repositories;

import com.torresj.nursing_sas_ope_info.entities.NurseWorkBolsaEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseWorkBolsaRepository extends CrudRepository<NurseWorkBolsaEntity, Long> {
    List<NurseWorkBolsaEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
}
