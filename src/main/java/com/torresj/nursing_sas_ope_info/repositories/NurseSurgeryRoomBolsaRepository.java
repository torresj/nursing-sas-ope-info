package com.torresj.nursing_sas_ope_info.repositories;

import com.torresj.nursing_sas_ope_info.entities.NurseSurgeryRoomBolsaEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseSurgeryRoomBolsaRepository extends CrudRepository<NurseSurgeryRoomBolsaEntity, Long> {
    List<NurseSurgeryRoomBolsaEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
}
