package ma.tna.ebanking.userservice.services;

import ma.tna.ebanking.userservice.api.TRestApi;
import ma.tna.ebanking.userservice.dtos.BenefBody;
import ma.tna.ebanking.userservice.dtos.T24BenefBody;
import ma.tna.ebanking.userservice.exceptions.UserServiceException;
import ma.tna.ebanking.userservice.model.Benef;
import ma.tna.ebanking.userservice.repositories.BenefRepo;
import ma.tna.ebanking.userservice.tools.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BenefService {
    private final BenefRepo benefRepo;
    private final TRestApi api;

    public BenefService(BenefRepo benefRepo, TRestApi api) {
        this.benefRepo = benefRepo;
        this.api = api;
    }
    public List<Benef> getCustomerBenef(String customerId,String profileId){
        List<Benef> benefs = (profileId==null||"".equals(profileId))?
                benefRepo.findBenefByCustomerId(customerId):
                benefRepo.findByCustomerIdAndProfileId(customerId,profileId);
        if(benefs!=null) return benefs;
        throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
    }

    /**
     * This methode is responsible for creating a new Benef
     * @param benef  benef data
     * @return created benef data
     * @throws NoSuchElementException if the customer does not exist
     */
    public Benef createBenef(Benef benef){

            BenefBody benefBody = new BenefBody();
            benefBody.setAccount(benef.getRib());
            Map<String,Object> body = new HashMap<>();
            body.put("benefeciary",benefBody);
            T24BenefBody response = api.validateBenef(body).get("data");
            if(response == null || !response.notAnEmptyBody()) throw new UserServiceException("Response body is empty", HttpStatus.NOT_FOUND.value());
            if(!response.validResponse()) throw new UserServiceException(response.getRetourList().get(0).getMsgRetour()+" | codeRetour: "+response.getRetourList().get(0).getCodeRetour(),HttpStatus.BAD_REQUEST.value());
            BenefBody body1 = response.getRetourList().get(0);
            benef.setCategory(body1.getCategory());
            benef.setCurrency(body1.getCurrency());
            benef.setInternal(body1.getInterne());
        try {
            return benefRepo.save(benef);
        }catch (Exception e ){
            throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
        }
    }

    /**
     * deletes a benef by his id
     * @param benefId  benef id
     */
    public void deleteBenef(Long benefId){
        benefRepo.deleteById(benefId);
    }

    /**
     * Uodates benef data
     * @param benef the new benef data
     * @return updated benef data
     * @throws NoSuchElementException if the benef does not exist
     */
    public Benef updateBenef(Benef benef){
        Optional<Benef> benefOptional = benefRepo.findById(benef.getId());
        if(benefOptional.isPresent()){
            Benef benef1 = benefOptional.get();
            benef1.setEmail("".equals(benef.getEmail())?benef1.getEmail():benef.getEmail());
            benef1.setFirstName("".equals(benef.getFirstName())?benef1.getFirstName(): benef.getFirstName());
            benef1.setRib("".equals(benef.getRib())?benef1.getRib():benef.getRib());
            benef1.setLastName("".equals(benef.getLastName())?benef1.getLastName():benef.getLastName());
            benef1.setPhone("".equals(benef.getPhone())?benef1.getPhone():benef.getPhone());
            return benefRepo.save(benef1);
        }
        throw new NoSuchElementException("Benef does not exist");
    }
}
