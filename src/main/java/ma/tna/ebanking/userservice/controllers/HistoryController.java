package ma.tna.ebanking.userservice.controllers;

import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.model.Benef;
import ma.tna.ebanking.userservice.repositories.BenefRepo;
import ma.tna.ebanking.userservice.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService<Benef> benefHistoryService;
    private final BenefRepo benefRepo;
    public HistoryController(HistoryService<Benef> benefHistoryService, BenefRepo benefRepo) {
        this.benefHistoryService = benefHistoryService;
        this.benefRepo = benefRepo;
    }

    @GetMapping("/benef/{id}")
    public HttpEntity<List<Benef>> benefHistryById(@PathVariable("id") int id){
        benefHistoryService.findHistory(benefRepo.findById( id).orElse(null),benefRepo);
        return ResponseEntity.ok(benefHistoryService.getHistoryList());
    }

}
