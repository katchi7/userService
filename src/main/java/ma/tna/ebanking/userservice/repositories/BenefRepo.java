package ma.tna.ebanking.userservice.repositories;

import ma.tna.ebanking.userservice.model.Benef;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(path = "benef")
public interface BenefRepo extends CrudRepository<Benef,Integer> {
    List<Benef> findBenefByCustomerId(int customerId);
}
