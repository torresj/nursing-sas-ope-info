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
public class NurseOpeProvisionalEntity extends SasWorker{
    @Column(updatable = false)
    private float total;

    @Column(updatable = false)
    private float op;

    @Column(updatable = false)
    private float con;

    @Column(updatable = false)
    private int position;
}
