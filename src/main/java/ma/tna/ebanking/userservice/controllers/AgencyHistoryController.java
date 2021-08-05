package ma.tna.ebanking.userservice.controllers;

import ma.tna.audit.controller.GenericAuditController;
import ma.tna.audit.service.GenericHistoryService;
import ma.tna.ebanking.userservice.model.Agency;
import ma.tna.ebanking.userservice.repositories.AgencyRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history/agency")
public class AgencyHistoryController extends GenericAuditController<Agency,String,Long, AgencyRepo> {
    public AgencyHistoryController(GenericHistoryService<Agency, String, Long> historyService, AgencyRepo repository) {
        super(historyService, repository);
    }
}
