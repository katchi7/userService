package com.tna.userservice.Repositories;

import com.tna.userservice.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends CrudRepository<Customer,Integer> {
    @Override
    List<Customer> findAll();
}
