package ma.tna.ebanking.userservice.services;

import ma.tna.ebanking.userservice.model.Benef;
import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.repositories.BenefRepo;
import ma.tna.ebanking.userservice.repositories.CustomerRepo;
import ma.tna.ebanking.userservice.tools.Constantes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BenefService {
    private final CustomerRepo customerRepo;
    private final BenefRepo benefRepo;

    public BenefService(CustomerRepo customerRepo, BenefRepo benefRepo) {
        this.customerRepo = customerRepo;
        this.benefRepo = benefRepo;
    }
    public List<Benef> getCustomerBenef(int customerId){
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        if(customerOptional.isPresent()){
            return benefRepo.findBenefByCustomerId(customerId);
        }
        throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
    }

    /**
     * This methode is responsible for creating a new Benef
     * @param benef  benef data
     * @return created benef data
     * @throws NoSuchElementException if the customer does not exist
     */
    public Benef createBenef(Benef benef){
        Optional<Customer> customerOptional = customerRepo.findById(benef.getCustomerId());
        if(customerOptional.isPresent()){
            return benefRepo.save(benef);
        }
        throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
    }

    /**
     * deletes a benef by his id
     * @param benefId  benef id
     */
    public void deleteBenef(int benefId){
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
        throw new NoSuchElementException("Benef deos not exist");
    }
}
