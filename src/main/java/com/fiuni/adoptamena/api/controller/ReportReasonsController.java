package com.fiuni.adoptamena.api.controller;

import com.fiuni.adoptamena.api.dto.ReportReasonsDto;
import com.fiuni.adoptamena.api.service.post.IReportReasonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reportReasons")
public class ReportReasonsController {

    @Autowired
    private IReportReasonsService reportReasonsService;

    @PostMapping({"", "/"})
    public ResponseEntity<ReportReasonsDto> create(@RequestBody() ReportReasonsDto reportReasonsDto) {
        ReportReasonsDto data = this.reportReasonsService.save(reportReasonsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }
}
