package com.project.cdio.services.impl;

import com.project.cdio.components.LocalizationUtils;
import com.project.cdio.convert.CustomerConvert;
import com.project.cdio.entities.CustomerEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.CustomerDTO;
import com.project.cdio.repositories.CustomerRepository;
import com.project.cdio.repositories.ManagementCustomerRepository;
import com.project.cdio.services.ICustomerService;
import com.project.cdio.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    private final CustomerConvert customerConvert;
    private final CustomerRepository customerRepository;
    private final ManagementCustomerRepository managementCustomerRepository;
    private final LocalizationUtils localizationUtils;

    @Override
    @Transactional
    public CustomerEntity createCustomer(CustomerDTO customerDTO) {
        CustomerEntity newCustomer = customerConvert.convertToEntity(customerDTO);
        newCustomer.setActive(1L);
        return customerRepository.save(newCustomer);
    }

    @Override
    public List<CustomerDTO> getCustomersByStaff(Long staffId) {
        //lay customer ma staff quan ly
        List<Long> customerIds = managementCustomerRepository.findByStaff_UserId(staffId)
                .stream()
                .map(mr -> mr.getCustomer().getCustomerId())
                .toList();

        //lay ra cac customer
        List<CustomerEntity> customers = customerRepository.findAllById(customerIds);

        return customers.stream()
                .map(customerConvert::convertToDto)
                .toList();
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) throws DataNotFoundException {
        Optional<CustomerEntity> optionaCustomer = customerRepository.findByCustomerId(customerId);
        if(optionaCustomer.isEmpty()){
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.FIND_CUSTOMER_FAILED));
        }
        CustomerEntity existingCustomer = optionaCustomer.get();
        return customerConvert.convertToDto(existingCustomer);
    }

    @Override
    @Transactional
    public CustomerEntity updateCustomer(long id, CustomerDTO customerDTO) {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findByCustomerId(id);
        CustomerEntity existingCustomer = optionalCustomer.get();
        if(existingCustomer != null){
           CustomerEntity customerUpdateEntity = customerConvert.updateToEntityFromDTO(customerDTO, existingCustomer,id);
            return customerRepository.save(customerUpdateEntity);
        }
        return null;
    }

    @Override
    @Transactional
    public CustomerEntity updateCustomerActive(long id,boolean active) {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findByCustomerId(id);
        CustomerEntity existingCustomer = optionalCustomer.get();
//        CustomerEntity customer;
        if(existingCustomer != null){
//            customer = customerConvert.convertToEntity(customerDTO);
            if(active == false){
                existingCustomer.setActive(0L);
            }else if(active == true){
                existingCustomer.setActive(1L);
            }
            return customerRepository.save(existingCustomer);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }
}
