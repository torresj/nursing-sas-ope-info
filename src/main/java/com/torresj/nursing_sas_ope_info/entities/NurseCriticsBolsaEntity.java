package com.torresj.nursing_sas_ope_info.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class NurseCriticsBolsaEntity extends SasWorker {
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
