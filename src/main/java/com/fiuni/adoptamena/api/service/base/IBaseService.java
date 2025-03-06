package com.fiuni.adoptamena.api.service.base;

import org.springframework.data.domain.Pageable;
import java.util.List;
public interface IBaseService<D, DTO> {

    public DTO getById(Integer id);

    List<DTO> getAll(Pageable pageable);

    void delete(Integer id);

    public abstract DTO create(DTO dto);

    public abstract DTO update(DTO dto);
}
