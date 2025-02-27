package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.domain.post.ReportReasonsDomain;
import com.fiuni.adoptamena.api.dto.ReportReasonsDto;
import com.fiuni.adoptamena.api.service.base.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReportReasonsService extends IBaseService<ReportReasonsDomain, ReportReasonsDto> {
    Page<ReportReasonsDto> getAllReportReasons(Pageable pageable, String description);
}
