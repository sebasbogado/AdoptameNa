package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.dao.post.IReportReasonsDao;
import com.fiuni.adoptamena.api.domain.post.ReportReasonsDomain;
import com.fiuni.adoptamena.api.dto.ReportReasonsDto;
import com.fiuni.adoptamena.api.service.base.BaseServiceImpl;
import com.fiuni.adoptamena.exception_handler.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ReportReasonsServiceImpl extends BaseServiceImpl<ReportReasonsDomain, ReportReasonsDto> implements IReportReasonsService {

    private static final Logger log = LoggerFactory.getLogger(PostTypeServiceImpl.class);

    @Autowired
    private IReportReasonsDao reportReasonsDao;

    @Override
    protected ReportReasonsDto convertDomainToDto(ReportReasonsDomain reportReasonsDomain) {
        log.info("converting ReportReasonsDomain to ReportReasonsDto");

        ReportReasonsDto reportReasonsDto = null;
        try {
            reportReasonsDto = new ReportReasonsDto();
            reportReasonsDto.setId(reportReasonsDomain.getId());
            reportReasonsDto.setDescription(reportReasonsDomain.getDescription());
        } catch (Exception e) {
            log.info("Error converting ReportReasonsDomain to ReportReasonsDto");
            new ErrorResponse("Error converting ReportReasonsDomain to ReportReasonDto", e.getMessage());
        }
        return reportReasonsDto;
    }

    @Override
    protected ReportReasonsDomain convertDtoToDomain(ReportReasonsDto reportReasonsDto) {
        log.info("converting ReportReasonsDto to ReportReasonsDomain");
        ReportReasonsDomain reportReasonsDomain = null;
        try {
            reportReasonsDomain = new ReportReasonsDomain();
            reportReasonsDomain.setId(reportReasonsDto.getId());
            reportReasonsDomain.setDescription(reportReasonsDto.getDescription());

            reportReasonsDomain.setIsDeleted(false);
        }catch (Exception e) {
            log.info("Error converting ReportReasonsDto to ReportReasonsDomain");
            new ErrorResponse("Error converting ReportReasonsDto to ReportReasonsDomain", e.getMessage());
        }
        return reportReasonsDomain;
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
        try {
            reportReasonsDto.setId(id);
            log.info("Updating report reason successful");
        }catch (Exception e) {
            new ErrorResponse("Error updating report reason.", e.getMessage());
        }
        return save(reportReasonsDto);
    }

    @Override
    public void deleteById(int id) {
        log.info("Deleting report reason");
        try {
            ReportReasonsDomain reportReasonsDomain = reportReasonsDao.findById(id).orElse(null);
            if (reportReasonsDomain != null && !reportReasonsDomain.getIsDeleted()) {
                //reportReasonsDao.delete(reportReasonsDomain); //descomentar para borrar en la base de datos
                reportReasonsDomain.setIsDeleted(true);         //comentar para borrar en la basse de datos
                reportReasonsDao.save(reportReasonsDomain);     //comentar para borrar en la basse de datos
                log.info("Report reason delete successful");
            }
        }catch (Exception e) {
            new ErrorResponse("Error deleting report reason.", e.getMessage());
        }
    }

    @Override
    public ReportReasonsDto getById(int id) {
        log.info("Getting report reason");
        ReportReasonsDto reportReasonsDto = null;
        try {
            Optional<ReportReasonsDomain> reportReasonsDomainOptional = reportReasonsDao.findById(id);
            if (reportReasonsDomainOptional.isPresent() && !reportReasonsDomainOptional.get().getIsDeleted()) {
                ReportReasonsDomain reportReasonsDomain = reportReasonsDomainOptional.get();

                reportReasonsDto = convertDomainToDto(reportReasonsDomain);
                log.info("Report reason get successful");
            }
        }catch (Exception e) {
            new ErrorResponse("Error getting report reason.", e.getMessage());
        }
        return reportReasonsDto;
    }

    @Override
    public Page<ReportReasonsDto> getAllReportReasons(Pageable pageable, String description) {
        log.info("Getting all report reasons");
        if (description == null ) {
            Page<ReportReasonsDomain> reportReasonsPage =  reportReasonsDao.findAllByIsDeletedFalse(pageable);
            return reportReasonsPage.map(this::convertDomainToDto);
        }

        Page<ReportReasonsDomain> reportReasonsPage = reportReasonsDao.findByDescriptionContainingAndIsDeletedFalse(description, pageable);
        return reportReasonsPage.map(this::convertDomainToDto);
    }
}
