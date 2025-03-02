package com.fiuni.adoptamena.api.service.post;

import com.fiuni.adoptamena.api.domain.post.ReportReasonsDomain;
import com.fiuni.adoptamena.api.dto.post.ReportReasonsDTO;
import com.fiuni.adoptamena.api.service.base.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReportReasonsService extends IBaseService<ReportReasonsDomain, ReportReasonsDTO> {
    List<ReportReasonsDTO> getAllReportReasons(Pageable pageable, String description);
}
