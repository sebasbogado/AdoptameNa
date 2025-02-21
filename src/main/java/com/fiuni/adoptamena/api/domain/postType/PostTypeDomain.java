package com.fiuni.adoptamena.api.domain.postType;

import com.fiuni.adoptamena.api.domain.base.BaseDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.io.Serial;

@Entity
@Table(name= "postTypes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostTypeDomain implements BaseDomain {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postType", nullable = false, unique = true )
    private Integer id;

    @Column(name = "str_name", nullable = false )
    private String name;

    @Column(name = "str_description", nullable = false )
    private String description;
}
