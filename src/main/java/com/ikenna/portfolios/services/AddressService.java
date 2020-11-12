package com.ikenna.portfolios.services;

import com.ikenna.portfolios.components.IAddressService;
import com.ikenna.portfolios.infos.Address;
import com.ikenna.portfolios.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements IAddressService {

    @Autowired
    AddressRepository addressRepository;

    public Address saveOrUpdateAddress(Address address) {
       /* try{
            address = addressRepository.save(address);
            return address;
        }catch(Exception e){
            throw new AddressException("The Skill, '" + address.getStreet() + "' already exist");
        }
        */
        return null;
    }

    public Iterable<Address> findAllAddress(){
        return addressRepository.findAll();
    }
}
