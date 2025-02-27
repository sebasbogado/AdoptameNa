package com.fiuni.adoptamena.api.dao.post;

import com.fiuni.adoptamena.api.domain.post.ReportReasonsDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReportReasonsDao extends CrudRepository<ReportReasonsDomain, Integer> {
    Page<ReportReasonsDomain> findAllByIsDeletedFalse(Pageable pageable);

    Page<ReportReasonsDomain> findByDescriptionContaining(String description, Pageable pageable);
}
