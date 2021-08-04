package ma.tna.ebanking.userservice.services;

import ma.tna.audit.model.Auditable;
import ma.tna.audit.service.GenericHistoryService;
import ma.tna.audit.service.HistoryService;
import org.springframework.stereotype.Service;
@Service
public class BasicHistoryService<T extends Auditable<T>,ID,AUDIT_ID extends Number & Comparable<AUDIT_ID>> extends GenericHistoryService<T,ID,AUDIT_ID> {}