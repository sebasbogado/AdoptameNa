package com.fiuni.adoptamena.api.domain.post;

import com.fiuni.adoptamena.api.domain.base.BaseDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Entity
@Table(name= "post_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostTypeDomain implements BaseDomain {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post_type", nullable = false, unique = true )
    private Integer id;

    @Column(name = "str_name", nullable = false )
    private String name;

    @Column(name = "str_description", nullable = false )
    private String description;

    @Column(name = "boolean_deleted")
    private Boolean isDeleted;
}
