package com.fiuni.adoptamena.api.domain.post;

import com.fiuni.adoptamena.api.domain.base.BaseDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Entity
@Table(name = "report_reasons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportReasonsDomain implements BaseDomain {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reason", unique = true, nullable = false)
    private Integer id;

    @Column(name = "str_description", nullable = false)
    private String description;

    @Column(name = "boolean_deleted")
    private Boolean isDeleted;
}
