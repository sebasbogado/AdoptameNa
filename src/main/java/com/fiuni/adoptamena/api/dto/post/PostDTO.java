package com.fiuni.adoptamena.api.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer idUser;

    private String title;

    private String content;

    private Integer idPostType;

    private String locationCoordinates;

    private String contactNumber;

    private String status;

    private int sharedCounter;

    private Date publicationDate;

}
