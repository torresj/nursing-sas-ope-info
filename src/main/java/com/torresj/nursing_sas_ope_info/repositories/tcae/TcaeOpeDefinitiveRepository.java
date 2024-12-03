package com.torresj.nursing_sas_ope_info.repositories.tcae;

import com.torresj.nursing_sas_ope_info.entities.tcae.TcaeOpeDefinitiveEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TcaeOpeDefinitiveRepository extends CrudRepository<TcaeOpeDefinitiveEntity, Long> {
    List<TcaeOpeDefinitiveEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
}
