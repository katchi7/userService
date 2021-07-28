package ma.tna.ebanking.userservice.controllers;

import controller.AuditController;
import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.repositories.CustomerRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.HistoryService;
@RestController
@RequestMapping("/history/customer")
public class CustomerHistoryController extends AuditController<Customer, CustomerRepo> {
    public CustomerHistoryController(HistoryService<Customer> historyService, CustomerRepo repository) {
        super(historyService, repository);
    }
}
