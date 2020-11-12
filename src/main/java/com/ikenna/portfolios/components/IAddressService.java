package com.ikenna.portfolios.components;

import com.ikenna.portfolios.infos.Address;
import org.springframework.stereotype.Component;

@Component
public interface IAddressService {
    /*public Address findAddressBySchool(String schoolName);
    public Address findAddressByPhoneNumber(String phoneNumber);
    public Address findAddressByIdentifier(String identifier);*/
    public Address saveOrUpdateAddress(Address address);
}
