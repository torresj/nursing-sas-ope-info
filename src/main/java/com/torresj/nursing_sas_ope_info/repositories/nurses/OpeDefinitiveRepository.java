package com.torresj.nursing_sas_ope_info.repositories.nurses;

import com.torresj.nursing_sas_ope_info.entities.nurses.ope.NurseOpeDefinitiveEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpeDefinitiveRepository extends CrudRepository<NurseOpeDefinitiveEntity, Long> {
    List<NurseOpeDefinitiveEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
}
