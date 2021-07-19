package ma.tna.ebanking.userservice.controllers;

import ma.tna.ebanking.userservice.model.Auditable;
import ma.tna.ebanking.userservice.services.HistoryService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class AuditController<T extends Auditable<T>,R extends CrudRepository<T,Integer> & RevisionRepository<T,Integer,Integer>>{
    private final HistoryService<T> historyService;
    private final R repository;
    public AuditController(HistoryService<T> historyService, R repository) {
        this.historyService = historyService;
        this.repository = repository;
    }
    @GetMapping("/{id}")
    public HttpEntity<List<T>> benefHistryById(@PathVariable("id") Integer id){
        historyService.findHistory(repository.findById(id).orElse(null), repository);
        return ResponseEntity.ok(historyService.getHistoryList());
    }
}
