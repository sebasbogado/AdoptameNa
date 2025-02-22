package com.fiuni.adoptamena.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer id_user;

    private String title;

    private String content;

    private Integer id_post_type;

    private String location_coordinates;

    private String contactNumber;

    private String status;

    private int sharedCouter;

    private Date datePost;

}
