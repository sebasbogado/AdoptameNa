package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.domain.post.ReportReasonsDomain;
import com.fiuni.adoptamena.api.dto.post.ReportReasonsDTO;
import com.fiuni.adoptamena.api.service.base.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReportReasonsService extends IBaseService<ReportReasonsDomain, ReportReasonsDTO> {
    Page<ReportReasonsDTO> getAllReportReasons(Pageable pageable, String description);
}
