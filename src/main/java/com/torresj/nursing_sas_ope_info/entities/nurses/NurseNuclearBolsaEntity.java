package com.torresj.nursing_sas_ope_info.entities.nurses;

import com.torresj.nursing_sas_ope_info.entities.SasWorker;
import com.torresj.nursing_sas_ope_info.entities.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class NurseNuclearBolsaEntity extends SasWorker {
    @Column(updatable = false)
    private String treaty;

    @Column(nullable = false)
    private Status general_admission;

    @Column(nullable = false)
    private Status specific_admission;

    @Column(updatable = false)
    private String exclusionCodes;

    @Column(updatable = false)
    private String experience;

    @Column(updatable = false)
    private String formation;

    @Column(updatable = false)
    private String others;

    @Column(updatable = false)
    private String total;
}
