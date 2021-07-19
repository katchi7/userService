package ma.tna.ebanking.userservice.services;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;


@Service("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Admin"); //To change body of generated methods, choose Tools | Templates.
    }
 
}