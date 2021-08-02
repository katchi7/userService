package ma.tna.ebanking.userservice.repositories;

import ma.tna.ebanking.userservice.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepo extends JpaRepository<Agency,String> {
}
