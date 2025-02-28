package com.fiuni.adoptamena.api.service.base;



public abstract class BaseServiceImpl<D, DTO> implements IBaseService<D, DTO> {

    protected abstract DTO convertDomainToDto(D dto);

    protected abstract D convertDtoToDomain(DTO dto);

}
