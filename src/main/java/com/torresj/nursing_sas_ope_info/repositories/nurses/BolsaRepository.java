package com.torresj.nursing_sas_ope_info.repositories.nurses;

import com.torresj.nursing_sas_ope_info.entities.nurses.bolsa.NurseBolsaEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BolsaRepository extends CrudRepository<NurseBolsaEntity, Long> {
    List<NurseBolsaEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
}
