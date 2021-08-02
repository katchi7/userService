package ma.tna.ebanking.userservice.services;

import ma.tna.ebanking.userservice.model.Agency;
import ma.tna.ebanking.userservice.repositories.AgencyRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AgencyService {
    private final AgencyRepo agencyRepo;

    public AgencyService(AgencyRepo agencyRepo) {
        this.agencyRepo = agencyRepo;
    }
    public List<Agency> getAllAgencies(){
        return agencyRepo.findAll();
    }
    public Agency getAgencyById(String agencyId){
        Optional<Agency> agencyOp = agencyRepo.findById(agencyId);
        if(agencyOp.isPresent()) return agencyOp.get();
        throw new NoSuchElementException("Agency does not exist");
    }
    public Agency updateAgency(Agency agency){
        Agency agency1 = agencyRepo.findById(agency.getId()).orElse(null);
        if(agency1 == null){
            //TODO : Search if agency exists in T24
        }
        return agencyRepo.save(agency);
    }
}
