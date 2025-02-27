package com.fiuni.adoptamena.api.controller.post;

import com.fiuni.adoptamena.api.dto.post.ReportReasonsDTO;
import com.fiuni.adoptamena.api.service.post.IReportReasonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportReasons")
public class ReportReasonsController {

    @Autowired
    private IReportReasonsService reportReasonsService;

    @PostMapping({"", "/"})
    public ResponseEntity<ReportReasonsDTO> create(@RequestBody() ReportReasonsDTO reportReasonsDto) {
        ReportReasonsDTO data = this.reportReasonsService.save(reportReasonsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<ReportReasonsDTO> update(@PathVariable(name = "id", required = true) int id, @RequestBody() ReportReasonsDTO reportReasonsDto)  {
        ReportReasonsDTO data = this.reportReasonsService.updateById(id, reportReasonsDto);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> delete(@PathVariable(name = "id", required = true) int id) {
        this.reportReasonsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Report Reason with id: " + id + "was deleted");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportReasonsDTO> getReportReasonById(@PathVariable(name = "id", required = true) int id) {
        ReportReasonsDTO data = this.reportReasonsService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page<ReportReasonsDTO>> getAllReportReasons(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort,
            @RequestParam(value = "description", required = false) String description) {

        String[] sortParams = sort.split(",");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(sortParams[0])));

        Page<ReportReasonsDTO> reportReasonsPage = reportReasonsService.getAllReportReasons(pageable, description);

        return ResponseEntity.status(HttpStatus.OK).body(reportReasonsPage);
    }

}
