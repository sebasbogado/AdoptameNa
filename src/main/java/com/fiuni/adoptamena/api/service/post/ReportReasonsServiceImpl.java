package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.dao.post.IReportReasonsDao;
import com.fiuni.adoptamena.api.domain.post.ReportReasonsDomain;
import com.fiuni.adoptamena.api.dto.post.ReportReasonsDTO;
import com.fiuni.adoptamena.api.service.base.BaseServiceImpl;
import com.fiuni.adoptamena.exception_handler.ErrorResponse;
import com.fiuni.adoptamena.exception_handler.exceptions.ResourceNotFoundException;
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
public class ReportReasonsServiceImpl extends BaseServiceImpl<ReportReasonsDomain, ReportReasonsDTO> implements IReportReasonsService {

    private static final Logger log = LoggerFactory.getLogger(PostTypeServiceImpl.class);

    @Autowired
    private IReportReasonsDao reportReasonsDao;

    @Override
    protected ReportReasonsDTO convertDomainToDto(ReportReasonsDomain reportReasonsDomain) {
        log.info("converting ReportReasonsDomain to ReportReasonsDTO");

        ReportReasonsDTO reportReasonsDto = null;
        try {
            reportReasonsDto = new ReportReasonsDTO();
            reportReasonsDto.setId(reportReasonsDomain.getId());
            reportReasonsDto.setDescription(reportReasonsDomain.getDescription());
        } catch (Exception e) {
            log.info("Error converting ReportReasonsDomain to ReportReasonsDTO");
            throw new ResourceNotFoundException("Error converting ReportReasonsDomain to ReportReasonsDTO");
            //new ErrorResponse("Error converting ReportReasonsDomain to ReportReasonDto", e.getMessage());
        }
        return reportReasonsDto;
    }

    @Override
    protected ReportReasonsDomain convertDtoToDomain(ReportReasonsDTO reportReasonsDto) {
        log.info("converting ReportReasonsDTO to ReportReasonsDomain");
        ReportReasonsDomain reportReasonsDomain = null;
        try {
            reportReasonsDomain = new ReportReasonsDomain();
            reportReasonsDomain.setId(reportReasonsDto.getId());
            reportReasonsDomain.setDescription(reportReasonsDto.getDescription());

            reportReasonsDomain.setIsDeleted(false);
        }catch (Exception e) {
            log.info("Error converting ReportReasonsDTO to ReportReasonsDomain");
            throw new ResourceNotFoundException("Error converting ReportReasonsDTO to ReportReasonsDomain");
            //new ErrorResponse("Error converting ReportReasonsDTO to ReportReasonsDomain", e.getMessage());
        }
        return reportReasonsDomain;
    }

    @Override
    public ReportReasonsDTO save(ReportReasonsDTO reportReasonsDto) {

        ReportReasonsDTO savedReportReasonDto = null;
        try {
            ReportReasonsDomain reportReasonsDomain = convertDtoToDomain(reportReasonsDto);
            ReportReasonsDomain savedReportReason = reportReasonsDao.save(reportReasonsDomain);
            log.info("Saved report reason successful");

            savedReportReasonDto = convertDomainToDto(savedReportReason);
        } catch (Exception e) {
            log.info("Error saving ReportReasonsDTO");
            throw new ResourceNotFoundException("Error creating a Report Reason");
            //new ErrorResponse("Error creating a Report Reason.", e.getMessage());
        }
        return savedReportReasonDto;
    }

    @Override
    public ReportReasonsDTO updateById(int id, ReportReasonsDTO reportReasonsDto) {
        try {
            reportReasonsDto.setId(id);
            log.info("Updating report reason successful");
        }catch (Exception e) {
            log.info("Error updating ReportReasonsDTO");
            throw new ResourceNotFoundException("Error updating report reason");
            //new ErrorResponse("Error updating report reason.", e.getMessage());
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
            log.info("Error deleting ReportReasonsDTO");
            throw new ResourceNotFoundException("Error deleting ReportReasonsDTO");
            //new ErrorResponse("Error deleting report reason.", e.getMessage());
        }
    }

    @Override
    public ReportReasonsDTO getById(int id) {
        log.info("Getting report reason");
        ReportReasonsDTO reportReasonsDto = null;
        try {
            Optional<ReportReasonsDomain> reportReasonsDomainOptional = reportReasonsDao.findById(id);
            if (reportReasonsDomainOptional.isPresent() && !reportReasonsDomainOptional.get().getIsDeleted()) {
                ReportReasonsDomain reportReasonsDomain = reportReasonsDomainOptional.get();

                reportReasonsDto = convertDomainToDto(reportReasonsDomain);
                log.info("Report reason get successful");
            }
        }catch (Exception e) {
            log.info("Error getting ReportReasonsDTO");
            throw new ResourceNotFoundException("Error getting ReportReasonsDTO");
            //new ErrorResponse("Error getting report reason.", e.getMessage());
        }
        return reportReasonsDto;
    }

    @Override
    public Page<ReportReasonsDTO> getAllReportReasons(Pageable pageable, String description) {
        log.info("Getting all report reasons");
        if (description == null ) {
            Page<ReportReasonsDomain> reportReasonsPage =  reportReasonsDao.findAllByIsDeletedFalse(pageable);
            return reportReasonsPage.map(this::convertDomainToDto);
        }

        Page<ReportReasonsDomain> reportReasonsPage = reportReasonsDao.findByDescriptionContainingAndIsDeletedFalse(description, pageable);
        return reportReasonsPage.map(this::convertDomainToDto);
    }
}
