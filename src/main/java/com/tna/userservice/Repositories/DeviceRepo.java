package com.tna.userservice.Repositories;

import com.tna.userservice.model.Customer;
import com.tna.userservice.model.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepo extends CrudRepository<Device, Integer> {
    List<Device> findDeviceByName(String name);
    //@Query("SELECT d from Device as d where d.id = ?1 and d.customer = ?2")
    Device findDeviceByIdAndAndCustomer(int id, Customer customer);
}
