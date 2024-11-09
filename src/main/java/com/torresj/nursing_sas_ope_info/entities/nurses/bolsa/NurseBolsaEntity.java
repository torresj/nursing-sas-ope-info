package com.torresj.nursing_sas_ope_info.entities.nurses.bolsa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
public class NurseBolsaEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(updatable = false)
    private String dni;

    @Column(updatable = false)
    private String name;

    @Column(updatable = false)
    private String surname;

    @Column(updatable = false)
    private String shift;

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
