package ma.tna.ebanking.userservice.repositories;

import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Image;
import ma.tna.ebanking.userservice.model.Otp;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepo extends CrudRepository<Customer,Integer> {
    @Override
    List<Customer> findAll();

    @Query("SELECT c.password from Customer as c where c.id = ?1")
    String findUserPassword(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Image as i set i.image = ?1 where i.id = ?2")
    void updateCustomerImage(String image,int id);

    @Query("SELECT image from Image as image where image.id = ?1")
    Image findCustomerImage(int userId);

    @Query("SELECT o from Otp as o where o.id = ?1")
    Otp findCustomerOtp(int customerId);

    @Transactional
    @Modifying
    @Query("UPDATE Otp as o set o.otp = ?1, o.otpExp = ?2 WHERE o.id = ?3")
    int saveOtp( String otp, LocalDateTime dateTime,int id);
}
