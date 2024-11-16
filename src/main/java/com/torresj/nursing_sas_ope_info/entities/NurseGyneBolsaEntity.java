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
@SuperBuilder
@Getter
public class NurseGyneBolsaEntity extends SasWorker {
    @Column(updatable = false)
    private String treaty;

    @Column(nullable = false)
    private Status status;

    @Column(updatable = false)
    private String exclusionCode;

    @Column(updatable = false)
    private String experience;

    @Column(updatable = false)
    private String formation;

    @Column(updatable = false)
    private String others;

    @Column(updatable = false)
    private String total;
}
