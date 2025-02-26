package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.dao.post.IReportReasonsDao;
import com.fiuni.adoptamena.api.domain.post.ReportReasonsDomain;
import com.fiuni.adoptamena.api.dto.ReportReasonsDto;
import com.fiuni.adoptamena.api.service.base.BaseServiceImpl;
import com.fiuni.adoptamena.exception_handler.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportReasonsServiceImpl extends BaseServiceImpl<ReportReasonsDomain, ReportReasonsDto> implements IReportReasonsService {

    private static final Logger log = LoggerFactory.getLogger(PostTypeServiceImpl.class);

    @Autowired
    private IReportReasonsDao reportReasonsDao;

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

        ReportReasonsDto savedReportReasonDto = null;
        try {
            ReportReasonsDomain reportReasonsDomain = convertDtoToDomain(reportReasonsDto);
            ReportReasonsDomain savedReportReason = reportReasonsDao.save(reportReasonsDomain);
            log.info("Saved report reason successful");

            savedReportReasonDto = convertDomainToDto(savedReportReason);
        } catch (Exception e) {
            new ErrorResponse("Error creating a Report Reason.", e.getMessage());
        }
        return savedReportReasonDto;
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
