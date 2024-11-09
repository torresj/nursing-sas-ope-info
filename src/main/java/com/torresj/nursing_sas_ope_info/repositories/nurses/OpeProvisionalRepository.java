package com.torresj.nursing_sas_ope_info.repositories.nurses;

import com.torresj.nursing_sas_ope_info.entities.nurses.ope.NurseOpeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpeProvisionalRepository extends CrudRepository<NurseOpeEntity, Long> {
    Optional<NurseOpeEntity> findBySurnameContainingIgnoreCaseAndDni(String surname, String dni);
}
