package ma.tna.ebanking.userservice.repositories;

import ma.tna.ebanking.userservice.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyRepo extends CrudRepository<Agency,String>, RevisionRepository<Agency,String,Long> {
    @Override
    List<Agency> findAll();
}
