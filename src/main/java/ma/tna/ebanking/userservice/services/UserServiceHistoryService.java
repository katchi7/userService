package ma.tna.ebanking.userservice.services;

import model.Auditable;
import org.springframework.stereotype.Service;
import service.HistoryService;

@Service
public class UserServiceHistoryService<T extends Auditable<T>> extends HistoryService<T> {
}
