package ma.tna.ebanking.userservice.services;

import ma.tna.audit.model.Auditable;
import ma.tna.audit.service.GenericHistoryService;
import org.springframework.stereotype.Service;
@Service
public class BasicHistoryService<T extends Auditable<T>,ID,AUDID extends Number & Comparable<AUDID>> extends GenericHistoryService<T,ID,AUDID> {}