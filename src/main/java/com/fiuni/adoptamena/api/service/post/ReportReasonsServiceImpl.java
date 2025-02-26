package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.domain.post.ReportReasonsDomain;
import com.fiuni.adoptamena.api.dto.ReportReasonsDto;
import com.fiuni.adoptamena.api.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportReasonsServiceImpl extends BaseServiceImpl<ReportReasonsDomain, ReportReasonsDto> implements IReportReasonsService {

    private static final Logger log = LoggerFactory.getLogger(PostTypeServiceImpl.class);

    @Override
    protected ReportReasonsDto convertDomainToDto(ReportReasonsDomain dto) {
        return null;
    }

    @Override
    protected ReportReasonsDomain convertDtoToDomain(ReportReasonsDto reportReasonsDto) {
        return null;
    }

    @Override
    public ReportReasonsDto save(ReportReasonsDto reportReasonsDto) {
        return null;
    }

    @Override
    public ReportReasonsDto updateById(int id, ReportReasonsDto reportReasonsDto) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ReportReasonsDto getById(int id) {
        return null;
    }
}
