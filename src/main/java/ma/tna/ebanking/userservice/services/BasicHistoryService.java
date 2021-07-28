package ma.tna.ebanking.userservice.services;

import ma.tna.audit.model.Auditable;
import ma.tna.audit.service.HistoryService;
import org.springframework.stereotype.Service;
@Service
public class BasicHistoryService<T extends Auditable<T>> extends HistoryService<T> { }