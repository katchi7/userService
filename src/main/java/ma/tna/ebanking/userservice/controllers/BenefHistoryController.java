package ma.tna.ebanking.userservice.controllers;

import ma.tna.audit.controller.GenericAuditController;
import ma.tna.audit.service.GenericHistoryService;
import ma.tna.ebanking.userservice.model.Benef;
import ma.tna.ebanking.userservice.repositories.BenefRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/history/benef")
public class BenefHistoryController extends GenericAuditController<Benef,Long,Long,BenefRepo> {
    public BenefHistoryController(GenericHistoryService<Benef, Long, Long> historyService, BenefRepo repository) {
        super(historyService, repository);
    }
}
