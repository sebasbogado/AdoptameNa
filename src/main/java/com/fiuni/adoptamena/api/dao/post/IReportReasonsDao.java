package com.fiuni.adoptamena.api.dao.post;

import com.fiuni.adoptamena.api.domain.post.ReportReasonsDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReportReasonsDao extends CrudRepository<ReportReasonsDomain, Integer> {
}
