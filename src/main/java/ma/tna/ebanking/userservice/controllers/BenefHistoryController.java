package ma.tna.ebanking.userservice.controllers;

import ma.tna.ebanking.userservice.model.Benef;
import ma.tna.ebanking.userservice.repositories.BenefRepo;
import ma.tna.ebanking.userservice.services.HistoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history/benef")
public class BenefHistoryController extends AuditController<Benef, BenefRepo> {
    public BenefHistoryController(HistoryService<Benef> historyService,BenefRepo repository) {
        super(historyService, repository);
    }
}
