package com.fiuni.adoptamena.api.service.race;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fiuni.adoptamena.api.domain.race.*;
import com.fiuni.adoptamena.api.dto.race.*;
import com.fiuni.adoptamena.api.service.base.BaseServiceImpl;
import com.fiuni.adoptamena.api.dao.animal.IAnimalDao;
import com.fiuni.adoptamena.api.dao.race.*;
import com.fiuni.adoptamena.exception_handler.exceptions.*;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RaceServiceImpl extends BaseServiceImpl<RaceDomain, RaceDTO> implements IRaceService {

    @Autowired
    private IRaceDao raceDao;

    @Autowired
    private IAnimalDao animalDao;

    @Override
    protected RaceDomain convertDtoToDomain(RaceDTO dto) {
        RaceDomain domain = new RaceDomain();
        domain.setId(dto.getId());
        domain.setName(dto.getName());
        return domain;
    }

    @Override
    protected RaceDTO convertDomainToDto(RaceDomain domain) {
        RaceDTO dto = new RaceDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setAnimalId(domain.getAnimal().getId());
        return dto;
    }

    @Override
    @Transactional
    public RaceDTO create(RaceDTO dto) {
        log.info("llega al service");
        RaceDomain domain = new RaceDomain();
        domain.setAnimal(animalDao.findByIdAndIsDeletedFalse(dto.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal no encontrado")));
        log.info("pasa verificacion de animal");
        domain.setName(dto.getName());
        domain.setIsDeleted(false);
        domain = raceDao.save(domain);
        return convertDomainToDto(domain);
    }

    @Override
    @Transactional
    public RaceDTO update(RaceDTO dto) {
        RaceDomain domain = raceDao.findByIdAndIsDeletedFalse(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Raza no encontrada"));
        domain.setAnimal(animalDao.findByIdAndIsDeletedFalse(dto.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal no encontrado")));
        
                domain.setName(dto.getName());
        domain = raceDao.save(domain);
        return convertDomainToDto(domain);
    }

    @Override
    public RaceDTO getById(Integer id) {
        RaceDomain domain = raceDao.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raza no encontrada"));
        return convertDomainToDto(domain);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        RaceDomain domain = raceDao.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raza no encontrada"));
        domain.setIsDeleted(true);
        raceDao.save(domain);
        return;
    }

    @Override
    public List<RaceDTO> getAll(Pageable pageable) {
        Page<RaceDomain> domain = raceDao.findAllByIsDeletedFalse(pageable);
        return convertDomainListToDtoList(domain.getContent());
    }

}
