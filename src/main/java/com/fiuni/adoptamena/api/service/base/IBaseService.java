package com.fiuni.adoptamena.api.service.base;


public interface IBaseService<D, DTO> {

    DTO save(DTO dto);
    void save(int id);
    DTO updateById(int id, DTO dto);

    void deleteById(int id);

    DTO getById(int id);
}
