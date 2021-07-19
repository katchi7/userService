package ma.tna.ebanking.userservice.controllers;

import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.repositories.CustomerRepo;
import ma.tna.ebanking.userservice.services.HistoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history/customer")
public class CustomerHistoryController extends AuditController<Customer, CustomerRepo> {
    public CustomerHistoryController(HistoryService<Customer> historyService, CustomerRepo repository) {
        super(historyService, repository);
    }
}
