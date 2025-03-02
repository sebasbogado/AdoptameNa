package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.domain.post.PostReportDomain;
import com.fiuni.adoptamena.api.dto.post.PostReportDto;
import com.fiuni.adoptamena.api.service.base.BaseServiceImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PostReportServiceImpl extends BaseServiceImpl<PostReportDomain, PostReportDto> implements IpostReportService {
    @Override
    protected PostReportDto convertDomainToDto(PostReportDomain dto) {
        return null;
    }

    @Override
    protected PostReportDomain convertDtoToDomain(PostReportDto postReportDto) {
        return null;
    }

    @Override
    public PostReportDto getById(Integer id) {
        return null;
    }

    @Override
    public List<PostReportDto> getAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public PostReportDto create(PostReportDto postReportDto) {
        return null;
    }

    @Override
    public PostReportDto update(PostReportDto postReportDto) {
        return null;
    }
}
