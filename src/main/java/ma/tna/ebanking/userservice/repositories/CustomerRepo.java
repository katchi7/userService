package ma.tna.ebanking.userservice.repositories;

import ma.tna.ebanking.userservice.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends CrudRepository<Customer,Integer> {
    @Override
    List<Customer> findAll();

    @Query("SELECT c.password from Customer as c where c.id = ?1")
    String findUserPassword(int id);
}
