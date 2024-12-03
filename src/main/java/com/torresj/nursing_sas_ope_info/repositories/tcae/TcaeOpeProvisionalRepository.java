package com.torresj.nursing_sas_ope_info.repositories.tcae;

import com.torresj.nursing_sas_ope_info.entities.tcae.TcaeOpeProvisionalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TcaeOpeProvisionalRepository extends CrudRepository<TcaeOpeProvisionalEntity, Long> {
    Optional<TcaeOpeProvisionalEntity> findBySurnameContainingIgnoreCaseAndDni(String surname, String dni);
}
