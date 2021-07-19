package ma.tna.ebanking.userservice.repositories;

import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepo extends CrudRepository<Device, Integer>, RevisionRepository<Device,Integer,Integer> {
    List<Device> findDeviceByName(String name);
    //@Query("SELECT d from Device as d where d.id = ?1 and d.customer = ?2")
    Device findDeviceByIdAndAndCustomer(int id, Customer customer);

    //@Query("SELECT d from Device  as d WHERE d.customer.id = ?1")
    List<Device> getDeviceByCustomer(Customer customer);
}
