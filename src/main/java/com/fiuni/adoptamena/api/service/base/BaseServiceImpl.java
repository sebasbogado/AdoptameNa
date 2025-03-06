package com.fiuni.adoptamena.api.service.base;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseServiceImpl<DOMAIN, DTO> implements IBaseService<DOMAIN, DTO> {

    protected abstract DTO convertDomainToDto(DOMAIN dto);

    protected abstract DOMAIN convertDtoToDomain(DTO dto);

    // Metodo para convertir una lista de Domain a una lista de DTO
    protected List<DTO> convertDomainListToDtoList(List<DOMAIN> domainList) {
        return domainList.stream()
                .map(this::convertDomainToDto)
                .collect(Collectors.toList());
    }

    // Metodo para convertir una lista de DTO a una lista de Domain (si necesitas lo
    // opuesto)
    protected List<DOMAIN> convertDtoListToDomainList(List<DTO> dtoList) {
        return dtoList.stream()
                .map(this::convertDtoToDomain)
                .collect(Collectors.toList());
    }

}
