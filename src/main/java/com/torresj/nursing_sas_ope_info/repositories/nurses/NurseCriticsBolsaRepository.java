package com.torresj.nursing_sas_ope_info.repositories.nurses;

import com.torresj.nursing_sas_ope_info.entities.nurses.NurseCriticsBolsaEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseCriticsBolsaRepository extends CrudRepository<NurseCriticsBolsaEntity, Long> {
    List<NurseCriticsBolsaEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
}
