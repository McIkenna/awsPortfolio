package com.ikenna.portfolios.repository;

import com.ikenna.portfolios.infos.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    /*
    Address findByPhoneNumber(String phoneNumber);
    Address findByIdentifier(String identifier);
    Address findBySchoolName(String schoolName);*/
}
