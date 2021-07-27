package ma.tna.ebanking.userservice.repositories;

import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Device;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DeviceRepo extends CrudRepository<Device, Integer>, RevisionRepository<Device,Integer,Integer> {
    List<Device> findDeviceByKey(String key);
    //@Query("SELECT d from Device as d where d.id = ?1 and d.customer = ?2")
    Device findDeviceByIdAndAndCustomer(int id, Customer customer);

    //@Query("SELECT d from Device  as d WHERE d.customer.id = ?1")
    List<Device> getDeviceByCustomer(Customer customer);

    @Query("SELECT d FROM Device as d where d.key = ?1 and  d.customer.id = ?2")
    Device findDeviceByKeyAndCustomerId(String deviceKey,Integer customerId);

    @Modifying
    @Transactional
    @Query("UPDATE Device as d set d.fingerprintActivated = ?1 where d.key = ?2 and d.customer.id = ?3")
    int updateDeviceFingerprint(boolean fingerprintActivated,String key, int id);
}
