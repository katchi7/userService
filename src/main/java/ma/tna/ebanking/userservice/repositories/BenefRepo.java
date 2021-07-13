package ma.tna.ebanking.userservice.repositories;

import ma.tna.ebanking.userservice.model.Benef;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "benef")
public interface BenefRepo extends CrudRepository<Benef,Integer> {
}
