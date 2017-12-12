package com.procurement.notice.controller;

import com.procurement.notice.model.dto.RequestDto;
import com.procurement.notice.model.dto.ResponseDto;
import com.procurement.notice.service.ReleaseService;
import com.procurement.notice.service.ReleaseServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/release")
public class MainController {

    private final ReleaseService releaseService;

    public MainController(final ReleaseServiceImpl releaseService) {
        this.releaseService = releaseService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseDto saveRecord(@RequestParam String cpid,
                                  @RequestParam String ocid,
                                  @RequestParam String tag,
                                  @RequestParam String initiationType,
                                  @RequestParam String language,
                                  @RequestBody RequestDto data) {
        return releaseService.saveRelease(cpid, ocid, tag, initiationType, language, data);
    }
}