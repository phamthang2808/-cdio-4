package com.project.cdio.services;

import com.project.cdio.entities.CustomerEntity;
import com.project.cdio.entities.RoomEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.CustomerDTO;
import com.project.cdio.models.RoomDTO;

import java.util.List;

public interface ICustomerService {

    CustomerEntity createCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getCustomersByStaff(Long staffId);
    CustomerDTO  getCustomerById(Long customerId) throws DataNotFoundException;
    CustomerEntity updateCustomer(long id, CustomerDTO customerDTO);
    CustomerEntity updateCustomerActive(long id,boolean active);
    void deleteCustomer(long id);
}
