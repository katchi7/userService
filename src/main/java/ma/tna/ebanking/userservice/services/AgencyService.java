package ma.tna.ebanking.userservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.api.TRestApi;
import ma.tna.ebanking.userservice.dtos.AgencyInfo;
import ma.tna.ebanking.userservice.dtos.T24AgencyResponse;
import ma.tna.ebanking.userservice.exceptions.AgencyServiceException;
import ma.tna.ebanking.userservice.model.Agency;
import ma.tna.ebanking.userservice.repositories.AgencyRepo;
import ma.tna.ebanking.userservice.tools.Constantes;
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

    @HystrixCommand(ignoreExceptions = { HystrixBadRequestException.class,AgencyServiceException.class }, commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = Constantes.AGENCY_INFO_HYSTRIX_TIMEOUT)
    })
    public List<Agency> getAllAgenciesInfos(){
        T24AgencyResponse agencyResponse = agencyInfoApi.getAgencyInfo(new HashMap<>());
        List<Agency> agencies = getAllAgencies();
        if(agencyResponse==null || agencyResponse.getAgence() == null || agencies == null) throw new AgencyServiceException("Agency response is empty", HttpStatus.NOT_FOUND);
        List<Agency> finalAgencyList = new ArrayList<>();
        for (AgencyInfo agencyInfo : agencyResponse.getAgence()) {
            boolean hasPosition = false;
            for (Agency agency : agencies) {
                if(agencyInfo.getiD().equals(agency.getId())){
                    hasPosition = true;
                    addT24Info(agency,agencyInfo);
                    finalAgencyList.add(agency);
                    break;
                }
            }
            if(!hasPosition){
                Agency agency = new Agency();
                agency.setId(agencyInfo.getiD());
                addT24Info(agency,agencyInfo);
                finalAgencyList.add(agency);
            }
        }
        return finalAgencyList;
    }
    public Agency getAgencyById(String agencyId){
        Optional<Agency> agencyOp = agencyRepo.findById(agencyId);
        if(agencyOp.isPresent()) return agencyOp.get();
        throw new NoSuchElementException("Agency does not exist");
    }

    @HystrixCommand(ignoreExceptions = { HystrixBadRequestException.class,AgencyServiceException.class },commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = Constantes.AGENCY_INFO_HYSTRIX_TIMEOUT)
    })
    public Agency updateAgency(Agency agency) {
        Agency agency1 = agencyRepo.findById(agency.getId()).orElse(null);
        if(agency1 == null){
            Map<String,Object> body = new HashMap<>();
            AgencyInfo agencyInfo = new AgencyInfo();
            agencyInfo.setID(agency.getId());
            body.put("agence",agencyInfo);
            T24AgencyResponse response = agencyInfoApi.getAgencyInfo(body);
            if(response==null) throw new AgencyServiceException("T24 response body is empty",HttpStatus.NOT_FOUND);
            if(response.EmptyAgency()) throw new AgencyServiceException("Agency with id "+agency.getId() +" Not found",HttpStatus.NOT_FOUND);
            agency =  agencyRepo.save(agency);
            addT24Info(agency,agencyInfo);
            return agency;
        }
        agency1.setLatitude(agency.getLatitude());
        agency1.setLongitude(agency.getLongitude());
        return agencyRepo.save(agency1);
    }
    private void addT24Info(Agency agency,AgencyInfo agencyInfo){
        agency.setName(agencyInfo.getNomAgence());
    }
}
