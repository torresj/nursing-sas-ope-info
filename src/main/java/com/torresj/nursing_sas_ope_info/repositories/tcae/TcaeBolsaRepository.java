package com.torresj.nursing_sas_ope_info.repositories.tcae;

import com.torresj.nursing_sas_ope_info.entities.tcae.TcaeBolsaEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TcaeBolsaRepository extends CrudRepository<TcaeBolsaEntity, Long> {
    List<TcaeBolsaEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
}
