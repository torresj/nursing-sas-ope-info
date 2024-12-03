package com.torresj.nursing_sas_ope_info.repositories.nurses;

import com.torresj.nursing_sas_ope_info.entities.nurses.NurseOpeProvisionalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NurseOpeProvisionalRepository extends CrudRepository<NurseOpeProvisionalEntity, Long> {
    Optional<NurseOpeProvisionalEntity> findBySurnameContainingIgnoreCaseAndDni(String surname, String dni);
}
