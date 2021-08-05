package ma.tna.ebanking.userservice.services;

import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.api.TRestApi;
import ma.tna.ebanking.userservice.dtos.AgencyInfo;
import ma.tna.ebanking.userservice.dtos.T24AgencyResponse;
import ma.tna.ebanking.userservice.exceptions.AgencyServiceException;
import ma.tna.ebanking.userservice.model.Agency;
import ma.tna.ebanking.userservice.repositories.AgencyRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
public class AgencyService {
    private final AgencyRepo agencyRepo;
    private final TRestApi agencyInfoApi;
    public AgencyService(AgencyRepo agencyRepo, TRestApi agencyInfoApi) {
        this.agencyRepo = agencyRepo;
        this.agencyInfoApi = agencyInfoApi;
    }
    public List<Agency> getAllAgencies(){
        return agencyRepo.findAll();
    }

    public List<Agency> getAllAgenciesInfos(){
        T24AgencyResponse agencyResponse = agencyInfoApi.getAgencyInfo(new HashMap<>());
        List<Agency> agencies = getAllAgencies();
        if(agencyResponse==null || agencyResponse.getAgence() == null || agencies == null) throw new AgencyServiceException("Agency response is empty", HttpStatus.NOT_FOUND);
        List<Agency> finalAgencyList = new ArrayList<>();
        for (AgencyInfo agencyInfo : agencyResponse.getAgence()) {
            for (Agency agency : agencies) {
                log.info(agency);
                log.info(agencyInfo);
                if(agencyInfo.getiD().equals(agency.getId())){
                    agency.setName(agencyInfo.getNomAgence());
                    finalAgencyList.add(agency);
                }
            }
        }
        return finalAgencyList;
    }
    public Agency getAgencyById(String agencyId){
        Optional<Agency> agencyOp = agencyRepo.findById(agencyId);
        if(agencyOp.isPresent()) return agencyOp.get();
        throw new NoSuchElementException("Agency does not exist");
    }
    public Agency updateAgency(Agency agency) {
        Agency agency1 = agencyRepo.findById(agency.getId()).orElse(null);
        if(agency1 == null){
            Map<String,Object> body = new HashMap<>();
            AgencyInfo agencyInfo = new AgencyInfo();
            agencyInfo.setID(agency.getId());
            body.put("agence",agencyInfo);
            T24AgencyResponse response = agencyInfoApi.getAgencyInfo(body);
            if(response==null) throw new AgencyServiceException("T24 response body is empty",HttpStatus.NOT_FOUND);
            if(response.getAgence() == null || response.getAgence().isEmpty()) throw new AgencyServiceException("Agency with id "+agency.getId() +" Not found",HttpStatus.NOT_FOUND);
        }
        return agencyRepo.save(agency);
    }
}
