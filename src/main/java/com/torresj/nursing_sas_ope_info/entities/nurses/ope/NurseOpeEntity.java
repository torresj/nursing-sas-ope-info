package com.torresj.nursing_sas_ope_info.entities.nurses.ope;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class NurseOpeEntity {
    @Id
    @Column(updatable = false)
    private Integer id;

    @Column(updatable = false)
    private String dni;

    @Column(updatable = false)
    private String name;

    @Column(updatable = false)
    private String surname;

    @Column(updatable = false)
    private String shift;

    @Column(updatable = false)
    private float total;

    @Column(updatable = false)
    private float op;

    @Column(updatable = false)
    private float con;
}
